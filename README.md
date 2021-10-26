# IPFS Node implementation for Android (and JVM)

## Description

This library allows you to embed an IPFS node into your android or JVM based application.

For a higher level implementation have a look at [KIPFS](https://github.com/danbrough/kipfs/tree/main/android)
which builds upon this library and also provides a client for accessing remote nodes.

It's based on the [gomobile-ipfs](https://github.com/ipfs-shipyard/gomobile-ipfs) project and uses the 
[gomobile](https://github.com/golang/go/wiki/Mobile) tool to generate an android bundle.

You can publish the library to your local maven repo with:
```bash
./gradlew publishToMavenLocal
```

The preBuild task will call `./gobuild.sh` to build the go code.

You can also include the pre-built library from the jitpack maven repo.

```gradle
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.danbrough:ipfs_mobile:master-SNAPSHOT")
}
```

## Example code

To run the code at [android/src/androidTest/java/danbroid/ipfsmobile/test/](android/src/androidTest/java/danbroid/ipfsmobile/test/)
on you connected android device,  run `./gradlew connectedDebugAndroidTest`

There is plenty more in my [KIPFS](https://github.com/danbrough/kipfs/tree/master/impl/android)  project.

For an introduction to how the go code works, `cd go && go run demo/kipfs_demo.go`.


