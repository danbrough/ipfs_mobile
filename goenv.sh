DIR=$(pwd)
[ -d gopath ] && mkdir -p gopath/bin
export GOPATH=$DIR/gopath
export PATH=$DIR/go/bin:$GOPATH/bin:$PATH
PACKAGES="kipfs/core kipfs/node kipfs/cids"
