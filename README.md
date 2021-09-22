# IPFS Node implementation for Android

## Description

This is currently an android only library for embedding an ipfs node in your mobile app.

It's based on the [gomobile-ipfs](https://github.com/ipfs-shipyard/gomobile-ipfs) project and uses the 
[gomobile](https://github.com/golang/go/wiki/Mobile) tool to generate an android bundle.

You can build the library to your local maven repo with:
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