import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
  id("com.android.library")
  `maven-publish`
}


android {
  compileSdk = 31

  defaultConfig {
    minSdk = 16
    targetSdk = 31
    versionCode = ProjectVersions.BUILD_VERSION
    versionName = ProjectVersions.VERSION_NAME
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    //consumerProguardFiles("consumer-rules.pro")
  }

  val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").java.srcDirs)
  }

  afterEvaluate {
    publishing {
      publications {

        publications {
          create<MavenPublication>("release") {
            from(components["release"])
            artifact(sourcesJar.get())
            artifactId = "android"
            groupId = ProjectVersions.GROUP_ID
            version = ProjectVersions.VERSION_NAME
          }
        }
      }
    }
  }

}




tasks {

  //builds the go library
  task<Exec>("gobuild"){
    commandLine("../gobuild.sh")
  }


  //build the library before anything else
  named("preBuild"){
    dependsOn("gobuild")
  }
}


