#!/bin/bash

cd `dirname $0`

AMD64=docker.io/danbrough/debby:latest@sha256:5c55e0ad1a7056e7e10783dc46f826c46477be66eacfd26e4994b1b6e29814bc
PLATFORM=$AMD64
NAME=debby_amd64

#PLATFORM=$ARM64
#NAME=debby_arm64

docker run -it --name $NAME  -h debby \
	-v /mnt/files:/mnt/files -v /mnt/files2:/mnt/files2 -v /home/dan:/home/dan  $PLATFORM

