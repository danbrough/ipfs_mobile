#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`

ARCH=`uname -m`
if [ "$ARCH" == "x86_64" ]; then
  ARCH=amd64
elif [ "$ARCH" == "aarch64" ]; then
    ARCH=arm64
fi

if [ -d "jvm/libs/linux/$ARCH" ] && [ "$1" != "force" ]; then
  echo jvm/libs/linux/$ARCH  exists. skipping go build.
  exit 0
fi


rm -rf jvm/libs/linux/$ARCH  2> /dev/null

source goenv.sh

cd go
unset ANDROID_HOME
#export CGO_CFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CGO_CXXFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CXXFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"


doBuild(){

  #go run golang.org/x/mobile/cmd/gomobile \
  echo running gomobile bind
    #gomobile \
    #bind -ldflags "-w"  -v -target=windows/$ARCH -tags=openssl  -javapkg go.kipfs  -o build \
   #$PACKAGES || exit 1

  gomobile \
    bind -ldflags "-w"  -v -x -work -target=linux/amd64   -javapkg go.kipfs  -o build \
   $PACKAGES || exit 1
}

echo building kipfs
go mod download || exit 1
go get -d  github.com/danbrough/mobile
go install  github.com/danbrough/mobile/cmd/gomobile
go install  github.com/danbrough/mobile/cmd/gobind
echo running "gomobile init" using `which gomobile`
gomobile init || exit 1

doBuild || exit 1


rm -rf ../core/src/main/java/go 2> /dev/null
unzip  build/core-sources.jar  -d ../core/src/main/java/
rm -rf ../core/src/main/java/META-INF
rm -rf ../jvm/libs/linux/$ARCH
mv build/libs ../jvm/libs/linux/$ARCH
rm -rf build



