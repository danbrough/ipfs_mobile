#!/bin/bash

cd `dirname $0`

amd64=docker.io/danbrough/archlinux:latest@sha256:e0864985db137571b02c6918677fe71dcbdc104cb373d84bf81b489aec1d2858

PLATFORM=amd64
NAME=archlinux


#PLATFORM=$AMD64
#NAME=debby_amd64

image=$(eval echo '$'$PLATFORM)
echo running `realpath setup.sh` in $image

docker run -it --name "${NAME}_${PLATFORM}"  -h debian \
	-v /mnt/files:/mnt/files -v /mnt/files2:/mnt/files2 \
	-v `realpath ..`:/home/kipfs/ipfs_mobile $image 
#	/home/kipfs/ipfs_mobile/docker/setup.sh
