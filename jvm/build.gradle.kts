import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources

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


  task<JavaExec>("run") {
    mainClass.set("danbroid.kipfs.jvm.Demo")
    //allJvmArgs = allJvmArgs + "-Djava.library.path=${file("libs/amd64")}"

    classpath = sourceSets["main"].runtimeClasspath
    dependsOn("jvmbuild")
    systemProperties["java.library.path"] = file("libs/amd64")
    systemProperties["offline"] = project.findProperty("offline") ?: false
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


val lib386 by tasks.registering(Jar::class) {
  from(file("libs/386"))
}
val libamd64 by tasks.registering(Jar::class) {
  from(file("libs/amd64"))
}

val libarm64 by tasks.registering(Jar::class) {
  from(file("libs/arm64"))
}
val libarm by tasks.registering(Jar::class) {
  from(file("libs/arm"))
}

publishing {

  publications {
/*    create<MavenPublication>("default") {
      from(components["java"])
      artifact(sourcesJar)
    }*/

    /*create<MavenPublication>("lib386") {
      artifactId = "lib386"
      artifact(lib386)
    }*/

/*    create<MavenPublication>("libamd64") {
      artifactId = "libamd64"
      artifact(libamd64)
    }*/

/*    create<MavenPublication>("libarm64") {
      artifactId = "libarm64"
      artifact(libarm64)
    }*/

/*    create<MavenPublication>("libarm") {
      artifactId = "libarm"
      artifact(libarm64)
    }*/
/*    listOf("386", "amd64", "arm", "arm64").forEach { libName ->
      create<MavenPublication>("lib$libName") {
        artifact(artifacts.artifact("libs/$libName/libgojni.so"))
        artifactId = "lib$libName"
      }
    }*/

  }
}

dependencies {
  api(project(":core"))
}