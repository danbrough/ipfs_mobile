#!/bin/bash

cd `dirname $0`
cd ..
[ ! -d libs/android  ] && mkdir -p libs/android
rsync -avHSx --delete android/src/main/jniLibs/ libs/android/
rsync -avHSx --delete libs/ h1:/srv/https/kipfs/libs/