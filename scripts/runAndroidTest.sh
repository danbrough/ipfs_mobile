#!/bin/bash


TEST_PACKAGE=danbroid.ipfsmobile.test

cd `dirname $0` && cd ..



if [ ! -z "$1" ]; then
  ./gradlew :android:installDebugAndroidTest
  adb shell am instrument -w -r -e debug false -e class $1  \
	$TEST_PACKAGE/androidx.test.runner.AndroidJUnitRunner
else
./gradlew connectedDebugAndroidTest

fi



