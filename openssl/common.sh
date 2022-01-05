#!/bin/bash


export SCRIPTDIR=$PWD
export SRC=$PWD/src

#echo LIBS: $LIBS
#echo SRC: $SRC

export OPENSSL_TAG=OpenSSL_1_1_1m
export CFLAGS="-Wno-macro-redefined"


if [ -z "$ANDROID_NDK_HOME" ]; then
  if [ -z "$ANDROID_NDK_ROOT" ]; then
    export ANDROID_NDK_HOME=/mnt/files/sdk/android/ndk/23.0.7599858
    echo ANDROID_NDK_HOME not set. Using the default: $ANDROID_NDK_HOME
  else
    export ANDROID_NDK_HOME=$ANDROID_NDK_ROOT
  fi
fi


if [ ! -d $SRC ]; then
  echo "downloading source .."
  git clone https://github.com/openssl/openssl.git $SRC || exit 1
  cd $SRC
  git checkout $OPENSSL_TAG  || exit 1
fi


cd $SRC
git clean -xdf
git reset --hard
git checkout $OPENSSL_TAG




