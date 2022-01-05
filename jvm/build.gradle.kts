import java.io.File

plugins {
  kotlin("jvm")
  `maven-publish`
}

group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME

dependencies {
  api(project(":core"))

  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
  // testImplementation("com.github.danbrough.ipfs_mobile:libamd64:0.10.0_06")
}

java {
  withSourcesJar()
}



val buildLinuxAmd64 by tasks.registering(Jar::class) {
  archiveFileName.set("linuxAmd64.jar")
  from(file("../build/libs/linux/libs/amd64"))
}

val buildLinuxArm64 by tasks.registering(Jar::class) {
  archiveFileName.set("linuxArm64.jar")
  from(file("../build/libs/linux/libs/arm64"))
}

val buildWin32 by tasks.registering(Jar::class) {
  archiveFileName.set("win32.jar")
  from(layout.projectDirectory.dir("../build/libs/win32"))
}



publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
    }

    listOf("win32", "linuxAmd64", "linuxArm64").forEach { arch ->
      create<MavenPublication>(arch) {
        artifactId = arch
        if (ProjectVersions.JITPACK_BUILD) {
          artifact(file("build/downloads/$arch.jar")) {
            builtBy(tasks.getAt("download${arch.capitalize()}"))
          }
        } else {
          artifact(tasks.getAt("build${arch.capitalize()}"))
        }
      }
    }
  }

  repositories {
    maven(ProjectVersions.MAVEN_REPO)
  }

}

