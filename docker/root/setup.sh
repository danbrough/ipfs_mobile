#!/bin/bash

echo building ipfs_mobile

#apt-get update && apt-get install -y build-essential default-jdk-headless unzip libssl-dev nano wget pkg-config

git clone https://github.com/danbrough/ipfs_mobile /home/kipfs
/home/kipfs/docker/setup.sh



