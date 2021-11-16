plugins {
  id("com.android.library")
  kotlin("android")
  `maven-publish`
}


android {
  compileSdk = ProjectVersions.SDK_VERSION

  defaultConfig {
    minSdk = 16
    targetSdk = ProjectVersions.SDK_VERSION
/*    versionCode = ProjectVersions.BUILD_VERSION
    versionName = ProjectVersions.VERSION_NAME*/
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

      repositories {
        maven(ProjectVersions.MAVEN_REPO)
      }
    }
  }
}


dependencies {
  api(project(":core"))
  implementation(AndroidX.core.ktx)

  androidTestImplementation(AndroidUtils.logging)
  androidTestImplementation(KotlinX.coroutines.android)

  androidTestImplementation(AndroidX.test.coreKtx)
  androidTestImplementation(AndroidX.test.rules)
  androidTestImplementation(AndroidX.test.runner)
  androidTestImplementation(AndroidX.test.ext.junitKtx)


}
