#!/bin/bash

cd ~/ipfs_mobile/go

# for Raspberry-pi 32bit
if [ "$ARCH" == "arm" ]; then
  export GOARM=5
fi

go mod download || exit 1
go get -d  github.com/danbrough/mobile
go install  github.com/danbrough/mobile/cmd/gomobile
go install  github.com/danbrough/mobile/cmd/gobind
echo running "gomobile init" using `which gomobile`
gomobile init || exit 1

echo building kipfs

BUILDDIR=/tmp/gobuild

if [ -d $BUILDDIR ]; then
    rm -rf $BUILDIR
fi

go run github.com/danbrough/mobile/cmd/gomobile \
    bind -ldflags "-w" \
    -work -v  \
    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl -o $BUILDDIR
