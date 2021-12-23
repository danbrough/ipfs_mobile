#!/bin/bash

cd `dirname $0`
source env.sh
install_gomobile

echo building kipfs moving to $IPFS_MOBILE/go

cd $IPFS_MOBILE/go

gomobile \
    bind -ldflags "-w"  -v  -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
    -o $BUILDDIR/libs/linux $PACKAGES


rm -rf $IPFS_MOBILE/core/src/main/java/go/
unzip -o -d  $IPFS_MOBILE/core/src/main/java/ $BUILDDIR/libs/linux/core-sources.jar

# For a static build of openssl  . Broken

#export CGO_CFLAGS="-fPIC -static -I$OPENSSL_LIBS/include"
#echo compiling with  -L$OPENSSL_LIBS/lib

#export CGO_LDFLAGS="-static -fPIC \
# -L$OPENSSL_LIBS/lib  -L/usr/lib/x86_64-linux-gnu -lcrypto -lssl -ldl -lpthread -lc "

#gomobile bind -ldflags "-w" -v \
#    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl \
#    -o $BUILDDIR/libs/linux $PACKAGES || exit 1

