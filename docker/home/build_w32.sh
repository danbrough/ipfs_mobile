#!/bin/bash


source ~/env.sh

OPENSSL=`realpath openssl/libs/win32`

~/ipfs_mobile/openssl/win32.sh

cd ~/ipfs_mobile/go

go mod download || exit 1
go get -d  github.com/danbrough/mobile
go install  github.com/danbrough/mobile/cmd/gomobile
go install  github.com/danbrough/mobile/cmd/gobind
#echo running "gomobile init" using `which gomobile`


export JAVA_HOME=/mnt/files2/windows/jdk
export CGO_CFLAGS="-fPIC -static -I$OPENSSL/include"
echo compiling with  -L$OPENSSL/lib
export CGO_LDFLAGS="-static -fPIC -L/usr/x86_64-w64-mingw32/lib/ -L$OPENSSL/lib -lcrypto -lcrypt32  -lws2_32 " #-Lssl -Lcrypt32 -Lmincor
go install github.com/danbrough/mobile/cmd/gomobile
gomobile  \
  bind -ldflags "-w" -x -v -target=windows/amd64  -tags=openssl  -javapkg go.kipfs  -o build \
   $PACKAGES || exit 1