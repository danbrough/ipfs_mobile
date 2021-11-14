plugins {
  kotlin("jvm")
  `maven-publish`
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)
// testImplementation("com.github.danbrough.ipfs_mobile:libamd64:0.10.0_06")
}


java {
  withSourcesJar()
}


group = ProjectVersions.GROUP_ID
version = ProjectVersions.VERSION_NAME

publishing {

  publications {
    create<MavenPublication>("default") {
      from(components["java"])
    }
  }


  repositories {
    maven(ProjectVersions.MAVEN_REPO)
  }

}