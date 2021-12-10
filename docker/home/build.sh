#!/bin/bash

cd ~/ipfs_mobile/go

export PACKAGES="kipfs/core kipfs/cids kipfs/pubsub"

go mod download || exit 1
go get -d  github.com/danbrough/mobile
#go install  github.com/danbrough/mobile/cmd/gomobile
#go install  github.com/danbrough/mobile/cmd/gobind
#echo running "gomobile init" using `which gomobile`
#gomobile init || exit 1

echo building kipfs

BUILDDIR=/home/kipfs/build

go run github.com/danbrough/mobile/cmd/gomobile \
    bind -ldflags "-w" \
    -work -v  \
    -target=linux/$ARCH -javapkg go.kipfs --tags=openssl -o $BUILDDIR $PACKAGES
