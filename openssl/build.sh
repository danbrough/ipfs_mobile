#!/bin/bash

cd `dirname $0`
source common.sh

LIBS=$SCRIPTDIR/libs/android

if [ -d $LIBS ]; then
    echo not building openssl as $LIBS exists
    exit 0
fi



echo LIBS: $LIBS
echo SRC: $SRC

PATH=$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/linux-x86_64/bin:$PATH


echo
#arch name: android version : folder
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


