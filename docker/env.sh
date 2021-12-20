
if [ -z "$GOROOT" ]; then
  export GOROOT=/opt/go
  export PATH=$GOROOT/bin:$PATH
fi

GOPATH=/cache


export GRADLE_USER_HOME=/cache/gradle

export PATH=$GOPATH/bin:$PATH

export GOPATH=$GOPATH:$IPFS_MOBILE


export PACKAGES="kipfs/core kipfs/cids kipfs/pubsub"
export BUILDDIR=$IPFS_MOBILE/build

ARCH=`uname -m`
if [ "$ARCH" == "x86_64" ]; then
  ARCH=amd64
elif [ "$ARCH" == "aarch64" ]; then
    ARCH=arm64
elif [ "$ARCH" == "armv7l" ]; then
	ARCH=arm
	export GOARM=5
fi
export ARCH

if [ -z "$ANDROID_NDK_ROOT" ]; then
  export ANDROID_NDK_ROOT=/opt/ndk
fi

if [ -z $JAVA_HOME ]; then
  export JAVA_HOME=/usr/lib/jvm/default/
fi

export IPFS_MOBILE

function install_gomobile() {
  cd $IPFS_MOBILE/go
  go mod download || exit 1
  go get -d  github.com/danbrough/mobile
  go install  github.com/danbrough/mobile/cmd/gomobile
  go install  github.com/danbrough/mobile/cmd/gobind
}
