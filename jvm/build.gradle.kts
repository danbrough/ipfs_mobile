plugins {
  kotlin("jvm")
  `maven-publish`
}

group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME

val urlPrefix = "https://h1.danbrough.org/maven/com/github/danbrough/ipfs_mobile"


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
  from(file("libs/linux/amd64"))
}

val buildLinuxArm64 by tasks.registering(Jar::class) {
  archiveFileName.set("linuxArm64.jar")
  from(file("libs/linux/arm64"))
}

val buildWin32 by tasks.registering(Jar::class) {
  archiveFileName.set("win32.jar")
  from(layout.projectDirectory.dir("libs/win32/amd64"))
}

/*
fun downloadDir(url: String, output: File) = tasks.registering(Exec::class) {
  println("downloadDir: $url")

  commandLine("wget", "-r", "-nH", "--cut-dirs=2", "-np", "-P", output.absolutePath, url)
}
*/

fun downloadFile(url: String, fileName: String) = tasks.registering(Exec::class) {
  //println("downloadFile: $url")
  doFirst {
    mkdir(file("build/downloads"))
  }
  workingDir = file("build/downloads")
  commandLine("wget", "-nv", url, "-O", fileName)
}

fun mavenDownload(name: String) =
  downloadFile("$urlPrefix/$name/$version/$name-$version.jar", "$name.jar")

val downloadWin32 by mavenDownload("win32")
val downloadLinuxAmd64 by mavenDownload("linuxAmd64")
val downloadLinuxArm64 by mavenDownload("linuxArm64")


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

tasks.named("publishToMavenLocal") {
  doFirst {
    println("RUNNING publishToMavenLocal")
  }
}

