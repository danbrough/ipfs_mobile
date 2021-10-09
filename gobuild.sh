#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`

if [ -d "android/src/main/jniLibs" ] && [ "$1" != "force" ]; then
  echo android/src/main/jniLibs exists. skipping go build.
  exit 0
fi




source goenv.sh

cd go

doBuild(){
  echo building kipfs
  go mod download || exit 1
  go install golang.org/x/mobile/cmd/gomobile
  go install golang.org/x/mobile/cmd/gobind
  gomobile init
  go run golang.org/x/mobile/cmd/gomobile \
    bind -ldflags "-w" -v -target=android -o gokipfs.aar -javapkg go.kipfs \
   $PACKAGES
}

doBuild || exit 1

[ -d tmp ] && rm -rf tmp
mkdir tmp
unzip gokipfs-sources.jar  -d tmp/
rm -rf ../android/src/main/java/go  > /dev/null
mv tmp/go ../android/src/main/java/
rm -rf tmp && mkdir tmp
unzip gokipfs.aar  -d tmp/
rm -rf ../android/src/main/jniLibs
mv tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp





