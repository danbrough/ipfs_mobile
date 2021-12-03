#!/bin/bash

cd `dirname $0` && cd ..

./gradlew -PLOCAL_MAVEN_REPO=file:///mnt/files2/repo/ \
	publishAllPublicationsToMavenRepository  && syncmaven 

