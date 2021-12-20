#!/bin/bash


. $IPFS_MOBILE/docker/env.sh

export OPENSSL_LIBS=$BUILDDIR/libs/openssl/win32
export OPENSSL=$OPENSSL_LIBS


$IPFS_MOBILE/openssl/win32.sh

install_gomobile

export JAVA_HOME=`realpath ~/win32_amd64_jdk/`

export CFLAGS="-Wno-macro-redefined"

export CC=/usr/bin/x86_64-w64-mingw32-gcc
export CXX=/usr/bin/x86_64-w64-mingw32-c++

#export JAVA_HOME=/mnt/files2/windows/jdkbak/
export CGO_CFLAGS="-fPIC -static -I$OPENSSL_LIBS/include"

echo compiling with  -L$OPENSSL_LIBS/lib

export CGO_LDFLAGS="-static -fPIC -L/usr/x86_64-w64-mingw32/lib/ -L$OPENSSL_LIBS/lib -lcrypto -lcrypt32  -lpthread -lws2_32 "

#-Lssl -Lcrypt32 -Lmincor

cd $IPFS_MOBILE/go

gomobile  \
  bind -ldflags "-w" -v -target=windows/amd64 -tags=openssl -javapkg go.kipfs  -o build \
   $PACKAGES || exit 1

cp -av build/libs/amd64/. $IPFS_MOBILE/build/libs/win32/
