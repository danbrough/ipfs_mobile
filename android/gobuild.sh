#!/bin/bash

echo running $0 at `date` at `pwd`

mkdir tmp && cd tmp
wget https://golang.org/dl/go1.16.8.linux-amd64.tar.gz > /dev/null 2>&1 || exit 1
tar xvpf go1.16.8.linux-amd64.tar.gz > /dev/null 2>&1


go/bin/go version




