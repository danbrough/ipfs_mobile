#!/bin/bash

# This script doesn't work
cd `dirname $0`

. env.sh


#export OPENSSL=$BUILDDIR/libs/

. ../openssl/linux.sh

echo openssl is at $OPENSSL_LIBS


#echo running "gomobile init" using `which gomobile`
#gomobile init || exit 1

install_gomobile
echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

export CGO_CFLAGS="-fPIC -static -I$OPENSSL_LIBS/include"
echo compiling with  -L$OPENSSL_LIBS/lib

export CGO_LDFLAGS="-static -fPIC \
 -L$OPENSSL_LIBS/lib  -L/usr/lib/x86_64-linux-gnu -lcrypto -lssl -ldl -lpthread -lc "

gomobile bind -ldflags "-w" -v \
    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
    -o $BUILDDIR/libs/linux $PACKAGES || exit 1



