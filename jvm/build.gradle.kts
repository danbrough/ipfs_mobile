plugins {
  kotlin("jvm")
  `maven-publish`
}

group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME



dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  // testImplementation("com.github.danbrough.ipfs_mobile:libamd64:0.10.0_06")
}

java {
  withSourcesJar()
}


val jvmBuild by tasks.registering(Exec::class) {
  commandLine("../jvmbuild.sh")
}

val linuxAmd64Jar by tasks.registering(Jar::class) {
  from(file("libs/linux/amd64"))
}
val win32Amd64Jar by tasks.registering(Jar::class) {
  from(file("libs/win32/amd64"))
}


publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
    }

    create<MavenPublication>("linuxAmd64Jar") {
      artifactId = "linuxAmd64"
      artifact(linuxAmd64Jar) {
        builtBy(jvmBuild)
      }
    }

    create<MavenPublication>("win32Amd64Jar") {
      artifactId = "win32Amd64"
      artifact(win32Amd64Jar) {
        builtBy(jvmBuild)
      }
    }
  }

  repositories {
    maven("kipfs") {
      url = file("../maven").toURI()
    }
  }
}



dependencies {
  api(project(":core"))
}