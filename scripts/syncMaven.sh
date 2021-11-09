#!/bin/bash

cd `dirname $0`
rsync -avHSx --delete ../maven/com/github/danbrough/ipfs_mobile/ \
  dan@h1:/srv/https/maven/com/github/danbrough/ipfs_mobile/
