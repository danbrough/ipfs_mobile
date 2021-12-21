#!/bin/bash

cd `dirname $0`
source env.sh
install_gomobile

echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

gomobile \
    bind -ldflags "-w" -x -v -work -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
    -o $BUILDDIR/libs/linux $PACKAGES

# For a static build of openssl  (doesn't work)

#export CGO_CFLAGS="-fPIC -static -I$OPENSSL_LIBS/include"
#echo compiling with  -L$OPENSSL_LIBS/lib

#export CGO_LDFLAGS="-static -fPIC \
# -L$OPENSSL_LIBS/lib  -L/usr/lib/x86_64-linux-gnu -lcrypto -lssl -ldl -lpthread -lc "

#gomobile bind -ldflags "-w" -v \
#    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
#    -o $BUILDDIR/libs/linux $PACKAGES || exit 1

