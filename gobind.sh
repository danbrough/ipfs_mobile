#!/bin/bash


cd `dirname $0`
echo running $0 at `date` at `pwd`


source goenv.sh

BUILD=`realpath builddir`
rm -rf $BUILD 2> /dev/null
mkdir $BUILD

cd go
export GOOS=android
export CGO_ENABLED=1

gobind -javapkg=go.kipfs -lang=go,java -outdir=$BUILD kipfs/cids kipfs/core