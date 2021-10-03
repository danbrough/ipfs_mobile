DIR=$(pwd)

if [ ! -z "$GOPATH" ]; then
	export GOPATH=$GOPATH:$DIR
else
	export GOPATH=$DIR
fi

export PATH=$DIR/go/bin:$GOPATH/bin:$PATH
PACKAGES="kipfs/core kipfs/cids"
