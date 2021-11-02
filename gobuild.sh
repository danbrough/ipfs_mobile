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
  go get -d  github.com/danbrough/mobile
  go install  github.com/danbrough/mobile/cmd/gomobile
  go install  github.com/danbrough/mobile/cmd/gobind

  #go install golang.org/x/mobile/cmd/gomobile
  #go install golang.org/x/mobile/cmd/gobind

  echo running "gomobile init" using `which gomobile`
  gomobile init || exit 1
  #go run golang.org/x/mobile/cmd/gomobile \
  gomobile \
    bind -ldflags "-w" -v  -tags=openssl  -target=android/386 -o kipfs.aar -javapkg go.kipfs  \
   $PACKAGES
}

doBuild || exit 1

[ -d tmp ] && rm -rf tmp
mkdir tmp
unzip kipfs-sources.jar  -d tmp/
rm -rf ../core/src/main/java/go   > /dev/null
mv tmp/go ../core/src/main/java/
rm -rf tmp && mkdir tmp
unzip kipfs.aar  -d tmp/
rm -rf ../android/src/main/jniLibs
mv tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp





