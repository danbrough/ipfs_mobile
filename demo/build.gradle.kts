
plugins {
  kotlin("jvm")
}


dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  implementation("com.github.danbrough.ipfs_mobile:jniAmd64:_")
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")
}


tasks {

  task<JavaExec>("run") {
    mainClass.set("danbroid.kipfs.demo.Demo")
    classpath = sourceSets["main"].runtimeClasspath
    systemProperties["offline"] = project.findProperty("offline") ?: false
  }
}



