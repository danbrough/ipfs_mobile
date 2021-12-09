#!/bin/bash

useradd    -M -s /bin/bash kipfs

cp -av /home/kipfs/docker/home/ /home/kipfs/
chown kipfs:kipfs -R /home/kipfs
