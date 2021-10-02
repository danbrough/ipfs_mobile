#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`

if [ -d "android/src/main/jniLibs" ]; then
  echo android/src/main/jniLibs exists. skipping build
  exit 0
fi


#DOWNLOAD=go1.17.1.linux-amd64.tar.gz
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

source goenv.sh

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
   $PACKAGES
}

doBuild || exit 1

[ -d tmp ] && rm -rf tmp
mkdir tmp
unzip gokipfs-sources.jar  -d tmp/
rm -rf ../android/src/main/java/go ../android/src/main/java/kipfs
mv tmp/go ../android/src/main/java/
mv tmp/kipfs ../android/src/main/java/
rm -rf tmp && mkdir tmp
unzip gokipfs.aar  -d tmp/
rm -rf ../android/src/main/jniLibs
mv tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp





