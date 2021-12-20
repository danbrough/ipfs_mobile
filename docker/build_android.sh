#!/bin/bash


export OPENSSL_LIBS=$IPFS_MOBILE/openssl/libs
echo OPENSSL_LIBS=$OPENSSL_LIBS

install_gomobile

cd $IPFS_MOBILE

if [ -d "android/src/main/jniLibs" ] && [ "$1" != "force" ]; then
  echo android/src/main/jniLibs exists. skipping go build.
  exit 0
fi


./openssl/build.sh

cd $IPFS_MOBILE/go


doBuild(){
  echo building kipfs
  gomobile  \
    bind -ldflags "-w" -v  -tags=openssl -target=android -o kipfs.aar -javapkg go.kipfs  \
   $PACKAGES
}

doBuild || exit 1

[ -d tmp ] && rm -rf tmp
mkdir tmp
unzip kipfs-sources.jar  -d tmp/
rm -rf ../core/src/main/java/go   > /dev/null
mv tmp/go ../core/src/main/java/
rm -rf tmp && mkdir tmp
unzip kipfs.aar  -d tmp/
rm -rf ../android/src/main/jniLibs
mv tmp/jni/ ../android/src/main/jniLibs/
rm -rf tmp






