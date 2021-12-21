#!/bin/bash


PLATFORM="$1"

if [ -z "$PLATFORM" ]; then
	PLATFORM=amd64
	echo using default platform $PLATFORM
fi 

NAME=ipfsmobile
#Place to store cache files
CACHEDIR=/mnt/files2/cache
#PLATFORM=$AMD64
#NAME=debby_amd64

echo running `realpath setup.sh` in $image

docker run -it --name "${NAME}_${PLATFORM}"  -h ${NAME}_${PLATFORM} \
	-v `realpath ../..`:/home/kipfs/ipfs_mobile \
	-v $CACHEDIR:/cache -v /tmp:/tmp \
	--platform=linux/$PLATFORM \
	danbrough/ipfsmobile:latest  \
	bash 
	#/home/kipfs/ipfs_mobile/docker/setup.sh
