plugins {
  kotlin("jvm")
  `maven-publish`
}



dependencies {
  implementation(Testing.junit4)
}


val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").java.srcDirs)
}

group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME

publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
      artifact(sourcesJar)
    }
  }
}
