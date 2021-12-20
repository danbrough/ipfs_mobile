#!/bin/bash

NAME=ipfsmobile

docker buildx build --platform linux/amd64,linux/arm64   -t danbrough/$NAME:latest --push .
#docker buildx build --platform linux/amd64,linux/arm64,linux/arm/v7   -t danbrough/debby:latest --push .
docker buildx imagetools inspect danbrough/$NAME:latest


