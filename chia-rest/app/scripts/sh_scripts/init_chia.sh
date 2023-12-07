#!/bin/bash 
function init_chia {
    set -a
    source .env
    set +a

    cd "${CHIA_APP_ROOT}"
    . ./activate
}
