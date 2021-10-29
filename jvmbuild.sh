#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`

if [ -d "jvm/libs" ] && [ "$1" != "force" ]; then
  echo jvm/libs exists. skipping go build.
  exit 0
fi


rm -rf jvm/libs 2> /dev/null

source goenv.sh

cd go
unset ANDROID_HOME
export CGO_CFLAGS="-I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux"



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
    bind -ldflags "-w" -x -v -target=linux/amd64 -javapkg go.kipfs -o build \
   $PACKAGES
}

doBuild || exit 1



#rm -rf ../jvm/src/main/java/go 2> /dev/null
#unzip  core-sources.jar  -d ../jvm/src/main/java/
#rm -rf ../jvm/src/main/java/META-INF
#mv libs ../jvm/libs




