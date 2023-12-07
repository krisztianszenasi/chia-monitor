

import os
import sqlite3
from contextlib import closing, suppress
from datetime import datetime
from subprocess import Popen, SubprocessError

from app.entities.backup import Backup, BackupProgress
from app.scripts.script_handler import create_backup
from app.utilities.singleton import SingletonMeta

DEFAULT_BACKUP_PATH = '$HOME/.chia/mainnet/db/vacuumed_blockchain_v2_mainnet.sqlite'
MAIN_DB_PATH = os.path.expandvars(
    '$HOME/.chia/mainnet/db/blockchain_v2_mainnet.sqlite',
)
BACKUP_PATH = os.path.expandvars(
    os.environ.get('BACKUP_PATH') or DEFAULT_BACKUP_PATH,
)


class BackupManager(metaclass=SingletonMeta):
    """Singleton manager class for handling database backups.

    Only one backup can be made at max. Under the hood it is using the cli
    command `chia db backup` through a dedicated script handler function.

    Information regarding the current backup file can be checked through the
    `current_backup` property. With the `current_backup_with_height` property
    the height can be retrieved as well however its a much more slower due the
    sqlite requests. If speed is important use the former.

    The `current_progress` property updates automatically to show to current
    progress. Wether a backup process is running or not can be checked via the
    `in_progress` property.

    A reference to the running process is stored in the `backup_process`
    attribute in a form a `subprocess.Popen`. It can be killed with the
    `cancel_process` function.
    """

    def __init__(self):
        self.backup_process: Popen = None

    @property
    def current_backup_with_height(self) -> Backup | None:
        """Retrieves the current backup with its height.

        Height calculation takes some time so only use
        if speed is not important.

        Returns none if there is no backup.

        Returns:
            Backup | None: current backup with its height
        """
        if backup := self.current_backup:
            backup.height = self._get_backup_height()
        return backup

    @property
    def current_backup(self) -> Backup | None:
        """Retrieves the current backup.

        It is significantly faster than the one with height.

        Returns none if there is no backup.

        Returns:
            Backup | None: current backup
        """
        return self.from_path(BACKUP_PATH)

    @property
    def current_progress(self) -> BackupProgress | None:
        """Retrieves the current progress of an ongoing backup process.

        It is determined simply by comparing the size of the backup file to the
        original database file. This file keeps growing until the end of the
        process.
    
        Returns None if there is no process running.

        Returns:
            BackupProgress | None: progress of backup process
        """
        main_db = self.from_path(MAIN_DB_PATH)
        if main_db and self.in_progress and self.current_backup:
            return BackupProgress(
                progress=self.current_backup.size / main_db.size,
                current_size=self.current_backup.size,
                goal_size=main_db.size,
            )
        return None

    @property
    def in_progress(self) -> bool:
        """Property for checking if there is a backup process running.

        Returns:
            bool: backup process in progress
        """
        return (
            self.backup_process is not None and
            self.backup_process.poll() is None
        )

    def create_backup(self, force_replace=False):
        """Creates a backup file from the main database file.

        Under the hood it uses the cli command `chia db backup`.
        If the `force_replace` parameter is set than the existing backup file
        will be deleted if it exists.

        Args:
            force_replace (bool, optional): force replace flag

        Raises:
            SubprocessError: Raised if a process is already in progress.
            FileExistsError: Raised if a backup already exists.
        """
        if self.in_progress:
            raise SubprocessError(
                'Existing backup process already in progress',
            )

        if self.current_backup and not force_replace:
            raise FileExistsError('Backup file already exists.')
        elif self.current_backup:
            self.delete_current()

        self.backup_process: Popen = create_backup()

    def delete_current(self):
        """Deletes the current backup file."""
        with suppress(FileNotFoundError):
            os.remove(BACKUP_PATH)

    def cancel_process(self):
        """Cancels the currently running backup process."""
        if self.in_progress:
            self.backup_process.kill()
            self.delete_current()
            self.backup_process = None

    def from_path(self, file_path: str) -> Backup | None:
        """Creates a `Backup` entity form a given filepath.

        Returns None if the file does not exist.

        Args:
            file_path (str): Path to backup file.

        Returns:
            Backup | None: backup information retrieved
        """
        if file_stat := self._get_file_stat(file_path):
            return Backup(
                name=file_path.split('/')[-1],
                size=file_stat.st_size,
                created=datetime.fromtimestamp(file_stat.st_mtime),
            )
        return None

    def _get_backup_height(self) -> int | None:
        """Retrieves the height of the current backup.

        It's a bit slow so only use if speed does not matter.

        Returns:
            int | None: blockchain height of the backup
        """
        try:
            with closing(sqlite3.connect(BACKUP_PATH)) as connection:
                with closing(connection.cursor()) as cursor:
                    return cursor.execute(
                        'SELECT height FROM full_blocks ORDER BY height DESC',
                    ).fetchone()[0]
        except sqlite3.OperationalError:
            return None

    def _get_file_stat(self, fila_path: str) -> os.stat_result | None:
        """Helper function that retrieves os stats for a given file.

        If the file does not exist the function returns None.

        Args:
            fila_path (str): Path to the file.

        Returns:
            os.stat_result | None: File stat retrieved
        """
        try:
            return os.stat(fila_path)
        except FileNotFoundError:
            return None
