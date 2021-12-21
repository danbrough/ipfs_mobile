#!/bin/bash

cd `dirname $0`
source env.sh
install_gomobile

echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

gomobile \
    bind -ldflags "-w" -v -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
    -o $BUILDDIR/libs/linux $PACKAGES

