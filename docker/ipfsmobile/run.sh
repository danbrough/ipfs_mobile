#!/bin/bash

amd64=docker.io/danbrough/ipfsmobile:latest@sha256:070f13442093d697026b0bfe36b27fb25e1fb96551e89d44e97dffd29befeb7c
arm64=docker.io/danbrough/ipfsmobile:latest@sha256:75cfbc8ae2299c8a13a721af85025db6467ca2ddf0820e547b90c14f2ce9a726

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
