buildscript {

  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:4.2.2")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
  }
}

apply("project.gradle.kts")

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}