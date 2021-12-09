#!/bin/bash

cd ~/ipfs_mobile/go

go mod download || exit 1
go get -d  github.com/danbrough/mobile
go install  github.com/danbrough/mobile/cmd/gomobile
go install  github.com/danbrough/mobile/cmd/gobind
echo running "gomobile init" using `which gomobile`
gomobile init || exit 1

echo building kipfs


