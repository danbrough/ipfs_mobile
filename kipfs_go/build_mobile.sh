#!/bin/bash

cd $(dirname $0)

#go mod download
#go install golang.org/x/mobile/cmd/gomobile
#go install golang.org/x/mobile/cmd/gobind

TARGET=android

go run golang.org/x/mobile/cmd/gomobile \
  bind -ldflags "-w" -v -target=$TARGET -o ../app/libs/gokipfs.aar -javapkg kipfs \
  kipfs/repo kipfs/cids kipfs/api kipfs/misc kipfs/core kipfs/node
