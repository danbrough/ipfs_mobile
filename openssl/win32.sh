#!/bin/bash

cd `dirname $0`

. ../docker/env.sh

SCRIPTDIR=$PWD


LIBS=$BUILDDIR/libs/openssl/win32/

if [ -d $LIBS ]; then
    echo not building openssl as $LIBS exists
    exit 0
fi

source common.sh

cd $SRC
git clean -xdf
git checkout $OPENSSL_TAG  || exit 1
export CC=/usr/bin/x86_64-w64-mingw32-gcc
export CXX=/usr/bin/x86_64-w64-mingw32-cpp
export WINDRES=/usr/bin/x86_64-w64-mingw32-windres

#./Configure --prefix="$LIBS" mingw64 no-shared  no-idea no-mdc2 no-rc5
./Configure --prefix="$LIBS" mingw64 no-shared || exit 1
make install_sw || exit 1
