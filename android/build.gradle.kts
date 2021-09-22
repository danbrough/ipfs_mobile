plugins {
  id("com.android.library")
  `maven-publish`
}

val projectVersion = "0.9.1_01"

android {
  compileSdk = 31

  defaultConfig {
    minSdk = 16
    targetSdk = 31
    versionCode = 1
    versionName = projectVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    //consumerProguardFiles("consumer-rules.pro")
  }

  val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").java.srcDirs)
  }

  afterEvaluate {
    publishing {
      val projectName = name
      publications {

        publications {
          create<MavenPublication>("release") {
            from(components["release"])
            artifact(sourcesJar.get())
            artifactId = projectName
            groupId = "danbroid.ipfsmobile"
            version = projectVersion
          }
        }
      }
    }
  }

}

