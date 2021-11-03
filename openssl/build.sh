#!/bin/bash

SCRIPTDIR=`dirname $0`
SCRIPTDIR=`realpath $SCRIPTDIR`

echo $SCRIPTDIR

LIBS=`realpath libs`
SRC=`realpath src`

echo LIBS $LIBS
echo SRC $SRC 

OPENSSL_TAG=OpenSSL_1_1_1l


rm -rf $LIBS && mkdir $LIBS

if [ ! -d $SRC ]; then
  echo "downloading source .."
  git clone git@github.com:openssl/openssl.git $SRC || exit 1
  cd $SRC
  git checkout $TAG  || exit 1
else
  echo "updating source"
  cd $SRC
  git reset --hard

  git pull
  git checkout $OPENSSL_TAG
fi

echo

for arch in "x86_64"; do
  echo compiling $arch
  cd $SRC
  git clean -xdf
  ./Configure android-$arch no-shared -D__ANDROID_API__=21 --prefix="$LIBS/$arch" || exit 1
  make || exit 1
  make install || exit 1
done





#./Configure android-x86_64 no-shared -D__ANDROID_API__=21 --prefix=`realpath ../x86_64`


