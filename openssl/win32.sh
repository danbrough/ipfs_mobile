#!/bin/bash

cd `dirname $0`
SCRIPTDIR=$PWD

OPENSSL_TAG=OpenSSL_1_1_1l

echo $SCRIPTDIR
LIBS=$SCRIPTDIR/libs/win32

if [ -d $LIBS ]; then
    echo not building openssl as $LIBS exists
    exit 0
fi

mkdir -p $LIBS

SRC=`realpath src`

echo LIBS: $LIBS
echo SRC: $SRC

export CFLAGS="-Wno-macro-redefined"


if [ ! -d $SRC ]; then
  echo "downloading source .."
  git clone https://github.com/openssl/openssl.git $SRC || exit 1
  cd $SRC
  git checkout $OPENSSL_TAG  || exit 1
fi

echo

cd $SRC
#git clean -xdf
#git checkout $OPENSSL_TAG  || exit 1
export CC=/usr/bin/x86_64-w64-mingw32-gcc
export CXX=/usr/bin/x86_64-w64-mingw32-cpp
export WINDRES=/usr/bin/x86_64-w64-mingw32-windres

#./Configure --prefix="$LIBS" mingw64 no-shared  no-idea no-mdc2 no-rc5
./Configure --prefix="$LIBS" mingw64 no-shared || exit 1
make install_sw || exit 1
