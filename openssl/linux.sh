#!/bin/bash

cd `dirname $0`

LIBS=$BUILDDIR/libs/openssl/linux/$ARCH/

if [ -d $LIBS ]; then
    echo not building openssl as $LIBS exists
    exit 0
fi

source common.sh
./Configure --prefix="$LIBS" linux-x86_64 no-shared || exit 1
make install_sw || exit 1

