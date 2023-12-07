# Prerequisite

## Chia blockchain

`chia-rest` is supposed to expose metrics about a Chia node. In order the use the application you will need a running instance of a chia. I recommend installing it from [soruce](https://docs.chia.net/installation/#from-source).

## Python

`chia-rest` is written in Python so in order to use it you will need it. It uses the latest version which is currently `3.12.0`.

I recommend using a Python virtual environment for installing it and all the dependencies. I usually go with [`pyenv`](https://github.com/pyenv/pyenv) paired with [`pyenv-virtualenv`](https://github.com/pyenv/pyenv-virtualenv).

### Install Python with pyenv

With `pyenv` and `pyenv-virtualenv` installed you can execute the following commands:

```
pyenv install 3.12.0

# create a virtual environment for packages
pyenv virtualenv chiarest3120 3.12.0

# set the environment as local enviroment for chia-rest
cd chia-rest
pyenv local chiarest3120 
```

### Install packages

With all that settled you are ready to install the package requirements for `chia-rest`.

```
cd chia-rest
pip install -r requirements.txt
```

## Environmental variables

`chia-rest` requires some environmental variables.
Here is a sample of that.

```
CHIA_APP_ROOT=.../chia-blockchian
API_KEY={secret key}

# optional variables with their default values
BACKUP_PATH=$HOME/.chia/mainnet/db/vacuumed_blockchain_v2_mainnet.sqlite
CHIA_ROOT='$HOME/.chia/mainnet'
RPC_ROOT=http://localhost
```

Copy these lines into your `.env`. It should be at the root of the `chia-rest` app.

## Android Studio

Android Studio is needed for running the application inside of a simulator. Import the `ChiaMonitor` project via `OPEN` > `.../ChiaMonitor`. There is also an `.apk` file, so you can install it to a physical device as well.

# Starting the server

First you need to start the `chia-blockchain`

```
cd chia-blockchian
. ./activate
chia start node
```

After that you can start the `chia-rest` server.

```
cd chia-rest
python chia_rest.py
```

# Starting the app

You can start the app be either using the simulator or installing it to a physical device.

## Host

If you choose the simulator than you host will be `http://10.0.2.2:5001` which is a virtual route for your machines loopback interface.

With the physical approach use your machines local ip address. For example `192.168.0.5`.

## Api key

The api key should match the one you defined in your `.env` file.