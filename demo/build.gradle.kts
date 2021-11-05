plugins {
  kotlin("jvm")
  application
}


application {
  mainClass.set("danbroid.kipfs.demo.Demo")
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  implementation("com.github.danbrough.ipfs_mobile:jniAmd64:_")
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")
}




