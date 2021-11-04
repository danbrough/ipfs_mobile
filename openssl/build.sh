#!/bin/bash

cd `dirname $0`
SCRIPTDIR=`realpath .`

echo $SCRIPTDIR
LIBS=`realpath libs`
SRC=`realpath src`

echo LIBS: $LIBS
echo SRC: $SRC

OPENSSL_TAG=OpenSSL_1_1_1l
export CFLAGS="-Wno-macro-redefined"

if [ -z "$ANDROID_NDK_HOME" ]; then
  export ANDROID_NDK_HOME=/mnt/files/sdk/android/ndk/23.0.7599858
  echo ANDROID_NDK_HOME not set. Using the default: $ANDROID_NDK_HOME
fi

PATH=$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH


if [ ! -d $SRC ]; then
  echo "downloading source .."
  git clone git@github.com:openssl/openssl.git $SRC || exit 1
  cd $SRC
  git checkout $TAG  || exit 1
fi

echo


for arch in arm64:21:arm64-v8a arm:16:armeabi-v7a x86:16:x86 x86_64:21:x86_64; do
#for arch in x86_64:21:x86_64; do
  echo compiling $arch
  cd $SRC
  git clean -xdf

  ARGS=(${arch//:/ })
  ARCH=${ARGS[0]}
  ANDROID_API=${ARGS[1]}
  INSTALLDIR="$LIBS/${ARGS[2]}"
  echo "ARCH $ARCH ANDROID_API: $ANDROID_API INSTALLDIR: $INSTALLDIR"
  [ -d $INSTALLDIR ] && rm -rf $INSTALLDIR
  ./Configure android-$ARCH no-shared -D__ANDROID_API__=$ANDROID_API --prefix="$INSTALLDIR" || exit 1
  make || exit 1
  make install_sw || exit 1
done





#./Configure android-x86_64 no-shared -D__ANDROID_API__=21 --prefix=`realpath ../x86_64`


