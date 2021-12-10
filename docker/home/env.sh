
export GOROOT=/opt/go
export GOPATH=/tmp/go
export PATH=$GOROOT/bin:$GOPATH/bin:$PATH
export GOPATH=$GOPATH:/home/kipfs/ipfs_mobile
export ANDROID_NDK_ROOT=/opt/ndk
export PACKAGES="kipfs/core kipfs/cids kipfs/pubsub"
export BUILDDIR=/home/kipfs/ipfs_mobile/build

# for Raspberry-pi 32bit
if [ "$ARCH" == "arm" ]; then
  export GOARM=5
fi

