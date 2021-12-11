#!/bin/bash

. $IPFS_MOBILE/docker/env.sh


#export OPENSSL=$BUILDDIR/libs/

. $IPFS_MOBILE/openssl/linux.sh

echo openssl is at $OPENSSL_LIBS


#echo running "gomobile init" using `which gomobile`
#gomobile init || exit 1

install_gomobile
echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

export CGO_CFLAGS="-fPIC -static -I$OPENSSL_LIBS/include"
echo compiling with  -L$OPENSSL_LIBS/lib

export CGO_LDFLAGS="-static -fPIC \
 -L$OPENSSL_LIBS/lib -lcrypto -lssl -ldl "

gomobile bind -ldflags "-w" -x -v \
    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
    -o $BUILDDIR/libs/linux $PACKAGES || exit 1



