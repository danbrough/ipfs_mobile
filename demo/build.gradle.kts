plugins {
  kotlin("jvm")
  application
}

val arch = System.getenv("os.arch")
println("arch is $arch")

application {
  mainClass.set("danbroid.kipfs.demo.Demo")
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  implementation("com.github.danbrough.ipfs_mobile:jniAmd64:_")
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")
}




