#!/bin/bash

. $IPFS_MOBILE/docker/env.sh



#echo running "gomobile init" using `which gomobile`
#gomobile init || exit 1

install_gomobile
echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

gomobile bind -ldflags "-w" -x -v \
    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl -o $BUILDDIR $PACKAGES || exit 1

