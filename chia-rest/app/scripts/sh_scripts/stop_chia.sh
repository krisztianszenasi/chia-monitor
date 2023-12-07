#!/bin/bash 
. app/scripts/sh_scripts/init_chia.sh
init_chia

# Check if the number of arguments is at least one
if [ "$#" -lt 1 ]; then
    echo "Usage: $0 <service>"
    exit 1
fi

# Get the service argument
service="$1"

# Run the chia rpc daemon start_service command with the specified service
chia rpc daemon stop_service "{\"service\": \"$service\"}"