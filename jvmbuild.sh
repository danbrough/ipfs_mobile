#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`
CC=`which cc`
CC_CLANG=`which clang`

echo "CC is $CC clang: $CC_CLANG"

if [ -d "jvm/libs" ] && [ "$1" != "force" ]; then
  echo jvm/libs exists. skipping go build.
  exit 0
fi


rm -rf jvm/libs 2> /dev/null

source goenv.sh

cd go
unset ANDROID_HOME
#export CGO_CFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CGO_CXXFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"
#export CXXFLAGS="-fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"


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
  echo running gomobile bind
  gomobile \
    bind -ldflags "-w"  -v -target=linux/amd64 -tags=openssl  -javapkg go.kipfs  -o build \
   $PACKAGES || exit 1
}

doBuild || exit 1


rm -rf ../core/src/main/java/go 2> /dev/null
unzip  build/core-sources.jar  -d ../core/src/main/java/
rm -rf ../core/src/main/java/META-INF
mv build/libs ../jvm/libs
rm -rf build



