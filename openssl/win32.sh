#!/bin/bash

cd `dirname $0`
SCRIPTDIR=`realpath .`

echo $SCRIPTDIR
LIBS=$(PWD)/libs/win32

if [ -d $LIBS ]; then
    echo not building openssl as $LIBS exists
    exit 0
fi

SRC=`realpath src`

echo LIBS: $LIBS
echo SRC: $SRC

OPENSSL_TAG=OpenSSL_1_1_1l
export CFLAGS="-Wno-macro-redefined"


if [ ! -d $SRC ]; then
  echo "downloading source .."
  git clone https://github.com/openssl/openssl.git $SRC || exit 1
  cd $SRC
  git checkout $OPENSSL_TAG  || exit 1
fi

echo

cd $SRC
git clean -xdf
git checkout $OPENSSL_TAG  || exit 1
export CC=/usr/bin/x86_64-w64-mingw32-gcc
export CXX=/usr/bin/x86_64-w64-mingw32-cpp
export WINDRES=/usr/bin/x86_64-w64-mingw32-windres

#./Configure --prefix="$LIBS" mingw64 no-shared  no-idea no-mdc2 no-rc5
./Configure --prefix="$LIBS" mingw64 no-shared || exit 1
make install_sw || exit 1

exit 0

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


