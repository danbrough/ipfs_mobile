#!/bin/bash

amd64=docker.io/danbrough/ipfsmobile:latest@sha256:28f677a575ab5e29db174ddd2ffb60b767e4077acc2d931a39b6a7bdc3879ca8
arm64=docker.io/danbrough/ipfsmobile:latest@sha256:8345609c95dfd453ad288151d164cadf1ff1f5dc6d179ff0544e139d5a0abca8

cd `dirname $0`

PLATFORM=amd64
NAME=ipfsmobile
#Place to store cache files
CACHEDIR=/mnt/files2/cache
#PLATFORM=$AMD64
#NAME=debby_amd64

image=$(eval echo '$'$PLATFORM)
echo running `realpath setup.sh` in $image

docker run -it --name "${NAME}_${PLATFORM}"  -h ${NAME}_${PLATFORM} \
	-v $CACHEDIR:/cache -v /tmp:/tmp \
	$image	\
	bash 
	#-v `realpath ../..`:/home/kipfs/ipfs_mobile $image \
	#/home/kipfs/ipfs_mobile/docker/setup.sh
