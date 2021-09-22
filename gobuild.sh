#!/bin/bash

echo running $0 at `date` at `pwd`
cd `dirname $0`
echo running in `pwd`

DOWNLOAD=go1.16.8.linux-amd64.tar.gz

doDownload(){
  echo downloading go ..
  wget -q https://golang.org/dl/$DOWNLOAD || exit 1
  tar xvpf $DOWNLOAD > /dev/null 2>&1
}

if [ ! -d "go" ]; then
  echo downloading go
  doDownload
fi

DIR=$(pwd)
[ -d gopath ] && mkdir -p gopath/bin
export GOPATH=$DIR/gopath
export PATH=$DIR/go/bin:$GOPATH/bin:$PATH

cd kipfs_go
go version
echo running go mod download in `pwd`


doBuild(){
  echo go is at `which go`
  echo go version `go version`
  echo building kipfs_go
  go mod download || exit 1
  go install golang.org/x/mobile/cmd/gomobile
  go install golang.org/x/mobile/cmd/gobind
  gomobile init
  go run golang.org/x/mobile/cmd/gomobile \
    bind -ldflags "-w" -v -target=android -o gokipfs.aar -javapkg kipfs \
    kipfs/repo kipfs/cids kipfs/api kipfs/misc kipfs/core kipfs/node
}

doBuild

[ -d tmp ] && rm -rf tmp
mkdir tmp
unzip gokipfs-sources.jar  -d tmp/
rm -rf ../android/src/main/java/
mv tmp ../android/src/main/java
rm -rf tmp && mkdir tmp
unzip gokipfs.aar  -d tmp/
rm -rf ../android/src/main/jniLibs
mv tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp





