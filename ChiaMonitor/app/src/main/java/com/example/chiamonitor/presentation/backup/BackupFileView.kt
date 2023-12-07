package com.example.chiamonitor.presentation.backup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chiamonitor.R
import com.example.chiamonitor.data.remote.ApiKeyInterceptor
import com.example.chiamonitor.data.remote.ChiaRestApi
import com.example.chiamonitor.data.remote.bakcup.BackupResponse
import com.example.chiamonitor.presentation.shared.AlertDialog
import com.example.chiamonitor.settings.ConnectionSettings
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.IllegalArgumentException

@Composable
fun BackupFileView(
    chiaRestApi: ChiaRestApi,
    onError: (Int) -> Unit
) {
    var backup by remember { mutableStateOf<BackupResponse?>(null) }
//    var chiaRestApi by remember { mutableStateOf<ChiaRestApi?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
//        if(chiaRestApi == null) {
//            chiaRestApi = buildChiaRestApi(connectionSettings)
//        }
        backup = getBackup(
            chiaRestApi = chiaRestApi!!,
            onError = onError
        )
    }

    if(backupExists(backup)) {
        BackupCard(
            backup = backup!!.backup!!,
            onDelete = {showDeleteDialog = true},
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    } else {
        CreateBackupView(
            onCreate = {
                scope.launch {
                    chiaRestApi?.createBackup()
                }
            }
        )
    }

    if(showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirmation = {
                scope.launch {
                    chiaRestApi?.deleteBackup()
                }
                backup = null
                showDeleteDialog = false
            },
            dialogTitle = stringResource(R.string.delete_backup),
            dialogText = stringResource(R.string.delete_backup_warning_text),
            icon = Icons.Default.Warning,
        )
    }
}


private suspend fun getBackup(
    chiaRestApi: ChiaRestApi,
    onError: (Int) -> Unit,
): BackupResponse? {
    try {
        val response = chiaRestApi.getBackup()

        return if (response.isSuccessful) {
            response.body()
        } else {
            when(response.code()) {
                401 -> onError(R.string.http_no_api_key)
                403 -> onError(R.string.http_invalid_api_key)
                else -> onError(R.string.http_wrong_host)
            }
            null
        }
    } catch (e: Exception) {
        onError(R.string.http_wrong_host)
        return null
    }
}


private fun backupExists(backupResponse: BackupResponse?): Boolean {
    return backupResponse != null && backupResponse!!.backup != null
}