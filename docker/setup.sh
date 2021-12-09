#!/bin/bash

useradd    -M -s /bin/bash kipfs
cp -av /home/kipfs/ipfs_mobile/docker/home/.  -t /home/kipfs/
chown kipfs:kipfs -R /home/kipfs

[ -d /usr/local/go ] && rm -rf /usr/local/go

ARCH=`uname -m`
GOVERSION=1.17.5

if [ $ARCH = "x86_64" ]; then
  DOWNLOAD=go$GOVERSION.linux-amd64.tar.gz
  ARCH=amd64
elif [ $ARCH = "aarch64" ]; then
  ARCH=arm64
  DOWNLOAD=go$GOVERSION.linux-arm64.tar.gz
elif [ $ARCH = "arm" ]; then
  ARCH=arm
  DOWNLOAD=go$GOVERSION.linux-armv6l.tar.gz
fi

sed -e /ARCH=/d /etc/environment

echo ARCH=$ARCH >> /etc/environment

if [ ! -d /usr/local/go ]; then
  cd /tmp
  echo downloading go from https://golang.org/dl/$DOWNLOAD ..
  wget -q https://golang.org/dl/$DOWNLOAD || exit 1
  tar xvpf $DOWNLOAD -C /usr/local
  rm $DOWNLOAD
fi

