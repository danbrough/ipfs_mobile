#!/bin/bash

amd64=docker.io/danbrough/ipfsmobile:latest@sha256:5aa8db97419c19381a772f3166a2efdb387c505abbd20d7e0bd7f5bd4be7f753
arm64=docker.io/danbrough/ipfsmobile:latest@sha256:e527364166e9842d0187371e072237359e86aa0d14b3c3f985f26789b2205b95

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
