#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`

ARCH=amd64

if [ -d "jvm/libs/w32/$ARCH" ] && [ "$1" != "force" ]; then
  echo jvm/libs/w32/$ARCH exists. skipping go build.
  exit 0
fi

rm -rf jvm/libs/w32/$ARCH


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

  export JAVA_HOME=/mnt/files2/windows/jdk
  gomobile \
    bind -ldflags "-w"  -v -x -work -target=windows/amd64   -javapkg go.kipfs  -o build \
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
rm -rf ../jvm/libs/win32/$ARCH; mkdir ../jvm/libs/win32
mv build/libs/* ../jvm/libs/win32/
rm -rf build



