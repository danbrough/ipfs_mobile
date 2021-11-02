
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

tasks {

  task<JavaExec>("run") {
    mainClass.set("danbroid.kipfs.jvm.Demo")
    classpath = sourceSets["main"].runtimeClasspath
    systemProperties["java.library.path"] = file("../jvm/libs/amd64")
    systemProperties["offline"] = project.findProperty("offline") ?: false
  }
}



