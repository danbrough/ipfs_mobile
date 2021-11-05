plugins {
  kotlin("jvm")
}


repositories {
  mavenLocal()
}


dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  implementation("com.github.danbrough.ipfs_mobile:libamd64:_")
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")

// testImplementation("com.github.danbrough.ipfs_mobile:libamd64:0.10.0_06")
}


tasks {

  task<JavaExec>("run") {
    mainClass.set("danbroid.kipfs.demo.Demo")
    classpath = sourceSets["main"].runtimeClasspath
    sourceSets["main"].runtimeClasspath.files.forEach {
      println("CLASSPATH: $it")
    }

    //systemProperties["java.library.path"] = file("../jvm/libs/amd64")
    systemProperties["offline"] = project.findProperty("offline") ?: false
  }
}



