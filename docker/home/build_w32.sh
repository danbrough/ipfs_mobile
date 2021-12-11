#!/bin/bash


source ~/env.sh

cd $SRC
OPENSSL=$SRC/openssl/libs/win32

./openssl/win32.sh

cd $SRC/go

go mod download || exit 1
go get -d  github.com/danbrough/mobile
go install  github.com/danbrough/mobile/cmd/gomobile
go install  github.com/danbrough/mobile/cmd/gobind
echo running "gomobile init" using `which gomobile`

export CFLAGS="-Wno-macro-redefined"
#export JAVA_HOME=`realpath ~/win32_amd64_jdk/`
export JAVA_HOME=/mnt/files2/windows/jdkbak/
export CGO_CFLAGS="-fPIC -static -I$OPENSSL/include"
echo compiling with  -L$OPENSSL/lib
export CGO_LDFLAGS="-static -fPIC -L/usr/x86_64-w64-mingw32/lib/ -L$OPENSSL/lib -lcrypto -lcrypt32  -lws2_32 " #-Lssl -Lcrypt32 -Lmincor
gomobile  \
  bind -ldflags "-w" -x -v -target=windows/amd64  -tags=openssl  -javapkg go.kipfs  -o build \
   $PACKAGES || exit 1