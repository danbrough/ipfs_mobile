#!/bin/bash

echo running $0 at `date` at `pwd`
cd ..

#wget https://golang.org/dl/go1.16.8.linux-amd64.tar.gz > /dev/null 2>&1 || exit 1
#tar xvpf go1.16.8.linux-amd64.tar.gz > /dev/null 2>&1

DIR=`pwd`
export PATH=$DIR/go/bin:$PATH
export GOPATH=$DIR/gopath

cd $GOPATH

go version
bash --norc --noprofile -i



