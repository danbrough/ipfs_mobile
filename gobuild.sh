#!/bin/bash

echo running $0 at `date` at `pwd`
cd `dirname $0`
echo running in `pwd`

DOWNLOAD=go1.16.8.linux-amd64.tar.gz

doDownload(){
  get https://golang.org/dl/$DOWNLOAD > /dev/null 2>&1 || exit 1
  tar xvpf $DOWNLOAD > /dev/null 2>&1
}

doDownload

DIR=$(pwd)
export PATH=$DIR/go/bin:$PATH
export GOPATH=$DIR/gopath

cd kipfs_go
go version
echo running go mod download in `pwd`

[ ! -d android/src/main/java ] && mkdir -p android/src/main/java
[ ! -d android/src/main/jniLibs ] && mkdir -p android/src/main/jniLibs

doBuild(){
  go mod download || exit 1
  go install golang.org/x/mobile/cmd/gomobile
  go install golang.org/x/mobile/cmd/gobind
  go run golang.org/x/mobile/cmd/gomobile \
    bind -ldflags "-w" -v -target=android -o gokipfs.aar -javapkg kipfs \
    kipfs/repo kipfs/cids kipfs/api kipfs/misc kipfs/core kipfs/node
}

doBuild

mkdir tmp
unzip gokipfs-sources.jar  -d tmp/
rsync -avHSx --delete tmp/ ../android/src/main/java/
rm -rf tmp && mkdir tmp
unzip gokipfs.aar  -d tmp/
rsync -avHSx --delete tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp





