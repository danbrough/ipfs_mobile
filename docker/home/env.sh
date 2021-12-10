
export GOROOT=/opt/go
export GOPATH=/tmp/go
export SRC=/home/kipfs/ipfs_mobile
export PATH=$GOROOT/bin:$GOPATH/bin:$PATH
export GOPATH=$GOPATH:$SRC
export ANDROID_NDK_ROOT=/opt/ndk
export PACKAGES="kipfs/core kipfs/cids kipfs/pubsub"
export BUILDDIR=$SRC/build

# for Raspberry-pi 32bit
if [ "$ARCH" == "arm" ]; then
  export GOARM=5
fi

