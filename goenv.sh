DIR=$(pwd)

if [ ! -z "$GOPATH" ]; then
	export GOPATH=$GOPATH:$DIR
else
	export GOPATH=$DIR
fi


DOWNLOAD=go1.17.6.linux-amd64.tar.gz
#DOWNLOAD=go1.17.5.linux-armv6l.tar.gz
#DOWNLOAD=go1.16.13.linux-amd64.tar.gz

doDownload(){
  if [ ! -f $DOWNLOAD ]; then
    echo downloading go ..
    wget -q https://golang.org/dl/$DOWNLOAD || exit 1
  fi
  tar xvpf $DOWNLOAD -C ~/ > /dev/null 2>&1
}

if [[ ! -d ~/go ]]; then
  doDownload
else
  echo "using existing go installation at ~/go"
fi

#github.com/ipfs/go-ipfs-files

#export PATH=~/go/bin:$GOPATH/bin:$PATH
export PATH=$GOPATH/bin:$PATH
PACKAGES="kipfs/core kipfs/cids kipfs/pubsub"
export OPENSSL_LIBS=`realpath openssl/libs`

echo go is at `which go`
echo go version `go version`
