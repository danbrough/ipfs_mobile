#!/bin/bash

amd64=docker.io/danbrough/ipfsmobile:latest@sha256:c4464c75b8131fea2e1a83ad3270cb7086330097c42836913154808b8eaee04f
arm64=docker.io/danbrough/ipfsmobile:latest@sha256:67080bf5d37024e7b79ca59d5b89460d93646381aca0dd197cdaf5d348246ff9

PLATFORM=arm64
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
