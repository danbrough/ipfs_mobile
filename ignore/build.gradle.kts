plugins {
  id("com.android.library")
  `maven-publish`
}


android {
  compileSdk = 31

  defaultConfig {
    minSdk = 16
    targetSdk = 31
    versionCode = 1
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
            artifactId = "ignore"
            groupId = ProjectVersions.GROUP_ID
            version = ProjectVersions.VERSION_NAME
          }
        }
      }
    }
  }

}

