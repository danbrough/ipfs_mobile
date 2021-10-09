DIR=$(pwd)

if [ ! -z "$GOPATH" ]; then
	export GOPATH=$GOPATH:$DIR
else
	export GOPATH=$DIR
fi



export PATH=~/go/bin:$GOPATH/bin:$PATH
PACKAGES="kipfs/core kipfs/cids"
