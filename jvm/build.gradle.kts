plugins {
  kotlin("jvm")
  `maven-publish`
}


repositories {
  mavenLocal()
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  // testImplementation("com.github.danbrough.ipfs_mobile:libamd64:0.10.0_06")
}

java {
  withSourcesJar()
}

/*
val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").java.srcDirs)
}
*/

val libamd64Jar by tasks.registering(Jar::class) {
  dependsOn("compileKotlin")
  from(file("libs/amd64").absolutePath)
}

/*val libarm64Jar by tasks.registering(Jar::class) {
  dependsOn("compileKotlin")
  from(file("libs/arm64/").absolutePath)
}*/

group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME

/*
artifacts.add("archives", file("libs/386/libgojni.so")) {
  type = "lib"
  name = "libs/386/libgojni.so"
}
*/

tasks {

  //builds the go library
  task<Exec>("jvmbuild") {
    commandLine("../jvmbuild.sh")
  }

  compileKotlin {
    dependsOn("jvmbuild")
    /*  doLast {
        named("jvmbuild").get().apply {
          actions.first().execute(this)
        }
      }*/
  }
}


publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
    }


    register<MavenPublication>("libamd64") {
      artifactId = "libamd64"
      artifact(libamd64Jar)
    }

  }
}



dependencies {
  api(project(":core"))
}