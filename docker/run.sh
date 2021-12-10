#!/bin/bash

cd `dirname $0`

AMD64=docker.io/danbrough/debby:latest@sha256:0651c5c85d2c9655439d7a3efb15a33c29a59830d1fb5f4cd3a0852f58221395
PLATFORM=$AMD64
NAME=debby_amd64

#PLATFORM=$ARM64
#NAME=debby_arm64

docker run -it --name $NAME  -h debby \
	-v /mnt/files:/mnt/files -v /mnt/files2:/mnt/files2 -v /home/dan:/home/dan  $PLATFORM

