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


val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").java.srcDirs)
}

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


/*
  named("compileKotlin") {
    dependsOn("jvmbuild")
  }
*/

  compileKotlin {
    doLast {
      named("jvmbuild").get().apply {
        actions.first().execute(this)
      }
    }
  }
}


/*val lib386 by tasks.registering(Jar::class) {
  from(file("libs/386"))
}


val libarm64 by tasks.registering(Jar::class) {
  from(file("libs/arm64"))
}
val libarm by tasks.registering(Jar::class) {
  from(file("libs/arm"))
}*/


val libamd64 by tasks.registering(Jar::class) {
  from(file("libs/amd64"))
  dependsOn("compileKotlin")
}

publishing {

  publications {
    create<MavenPublication>("default") {
      from(components["java"])
      artifact(sourcesJar)
    }

    create<MavenPublication>("libamd64") {
      artifactId = "libamd64"
      artifact(libamd64)

    }

  }
}

dependencies {
  api(project(":core"))
}