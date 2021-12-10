#!/bin/bash

cd `dirname $0`

PLATFORM=amd64
NAME=debian

amd64=docker.io/danbrough/debian:latest@sha256:71d2fe20a3926197a54871acb45b3051f801f4dab84af50d5f085546f5cfda69
arm64=docker.io/danbrough/debian:latest@sha256:7ac81164fa8718440d7d430f8172e2c4622abb2c60fa92faf53e24385558ee6b

#PLATFORM=$AMD64
#NAME=debby_amd64

image=$(eval echo '$'$PLATFORM)
echo running `realpath setup.sh` in $image

docker run -it --name $NAME_$PLATFORM  -h debian \
	-v /mnt/files:/mnt/files -v /mnt/files2:/mnt/files2 \
	-v `realpath ..`:/home/kipfs/ipfs_mobile $image \
	/home/kipfs/ipfs_mobile/docker/setup.sh
