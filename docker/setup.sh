#!/bin/bash


useradd    -M -s /bin/bash kipfs
cp -av /home/kipfs/ipfs_mobile/docker/home/.  -t /home/kipfs/
chown kipfs:kipfs -R /home/kipfs
source /home/kipfs/env.sh


ARCH=`uname -m`
GOVERSION=1.17.5

if [ $ARCH = "x86_64" ]; then
  DOWNLOAD=go$GOVERSION.linux-amd64.tar.gz
  ARCH=amd64
elif [ $ARCH = "aarch64" ]; then
  ARCH=arm64
  DOWNLOAD=go$GOVERSION.linux-arm64.tar.gz
elif [ $ARCH = "armv7l" ]; then
  ARCH=arm
  DOWNLOAD=go$GOVERSION.linux-armv6l.tar.gz
fi

sed -e /ARCH=/d /etc/environment

echo ARCH=$ARCH >> /etc/environment


cd /tmp
echo downloading go from https://golang.org/dl/$DOWNLOAD ..
wget -q https://golang.org/dl/$DOWNLOAD || exit 1
tar xvpf $DOWNLOAD -C /opt
rm $DOWNLOAD


if [ "$ARCH" = "amd64" ] && [ ! -d $ANDROID_NDK_ROOT ]; then
  echo "downloading android ndk"
  cd /tmp
  DOWNLOAD=https://dl.google.com/android/repository/android-ndk-r23b-linux.zip
  wget -q $DOWNLOAD -O ndk.zip
  unzip ndk.zip  -d /opt
  mv /opt/android-ndk* $ANDROID_NDK_ROOT
  chown kipfs:kipfs -R $ANDROID_NDK_ROOT
  rm ndk.zip  
fi