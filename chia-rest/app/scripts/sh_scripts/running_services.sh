#!/bin/bash 
. app/scripts/sh_scripts/init_chia.sh
init_chia
chia rpc daemon running_services
