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
    maven("https://jitpack.io")
  }
}