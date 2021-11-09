buildscript {


  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:_")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
  }
}

apply("project.gradle.kts")

allprojects {
  repositories {

    google()
    mavenCentral()
    maven("https://h1.danbrough.org/maven")

   // maven("https://jitpack.io")
  }

  tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
      kotlinOptions {
        jvmTarget = ProjectVersions.KOTLIN_JVM_VERSION
      }
    }

    withType<JavaCompile>() {
      sourceCompatibility = ProjectVersions.JAVA_VERSION.toString()
      targetCompatibility = ProjectVersions.JAVA_VERSION.toString()
    }

    withType<Test> {
      useJUnit()

      testLogging {
        events("standardOut", "started", "passed", "skipped", "failed")
        showStandardStreams = true
    /*    outputs.upToDateWhen {
          false
        }*/
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
      }

    }

  }
}