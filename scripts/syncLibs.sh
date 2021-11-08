#!/bin/bash

cd `dirname $0`
rsync -avHSx --delete ../libs/ h1:/srv/https/kipfs/libs/