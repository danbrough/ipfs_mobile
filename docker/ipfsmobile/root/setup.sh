#!/bin/bash

useradd  -M -s /bin/bash kipfs

if [ ! -d /home/kipfs/ipfs_mobile ]; then
  chown kipfs:kipfs -R /home/kipfs
  su -l kipfs -c "git clone  https://github.com/danbrough/ipfs_mobile"
fi

source /home/kipfs/ipfs_mobile/docker/env.sh

ARCH=`uname -m`
GOVERSION=1.17.6
#GOVERSION=1.16.12

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

sed -i -e /ARCH=/d  -e /JAVA_HOME=/d /etc/environment
echo ARCH=$ARCH >> /etc/environment

if [ ! -d /opt/go ]; then
  cd /tmp
  echo downloading go from https://golang.org/dl/$DOWNLOAD ..
  wget -q https://golang.org/dl/$DOWNLOAD || exit 1
  tar xvpf $DOWNLOAD -C /opt > /dev/null || exit 1
  rm $DOWNLOAD
fi



if [ "$ARCH" = "amd64" ] && [ ! -d $ANDROID_NDK_ROOT ]; then
  cd /tmp
  DOWNLOAD=https://dl.google.com/android/repository/android-ndk-r23b-linux.zip
  echo "downloading android ndk from $DOWNLOAD"
  wget -q $DOWNLOAD -O ndk.zip
  unzip ndk.zip  -d /opt > /dev/null
  mv /opt/android-ndk* $ANDROID_NDK_ROOT
  chown kipfs:kipfs -R $ANDROID_NDK_ROOT
  rm ndk.zip
  echo installing android-sdk-platform-tools android-sdk-platform-23
  apt install -y android-sdk-platform-tools android-sdk-platform-23
  echo "installing mingw-64"
  apt install -y mingw-w64/stable
fi





