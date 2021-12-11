#!/bin/bash

cd `dirname $0`

amd64=docker.io/danbrough/archlinux:latest@sha256:ab927dbd046c0f9833512c0eac2cb113b50acbdb1349f35a98eae764c740a675
PLATFORM=amd64
NAME=archlinux


#PLATFORM=$AMD64
#NAME=debby_amd64

image=$(eval echo '$'$PLATFORM)
echo running `realpath setup.sh` in $image

docker run -it --name "${NAME}_${PLATFORM}"  -h archlinux_$PLATFORM \
	-v /mnt/files:/mnt/files -v /mnt/files2:/mnt/files2 \
	-v `realpath ../..`:/home/kipfs/ipfs_mobile $image
#	/home/kipfs/ipfs_mobile/docker/setup.sh
