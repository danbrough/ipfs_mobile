#!/bin/bash


cd `dirname $0`
SCRIPTDIR=`pwd`
echo running $0 at `date` at $SCRIPTDIR



source goenv.sh


./openssl/build.sh

cd $SCRIPTDIR/go
echo OPENSSL_LIBS=$OPENSSL_LIBS

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
    bind -ldflags "-w" -v -work  -tags=openssl -target=android -o kipfs.aar -javapkg go.kipfs  \
   $PACKAGES
}

doBuild || exit 1




