import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly


plugins {
  kotlin("jvm")
  id("com.github.johnrengelman.shadow")
}


/*System.getProperties().keys.forEach {
  println("$it -> ${System.getProperty(it?.toString())}")
}*/

val arch = System.getProperty("os.arch")!!.let {
  if (it == "aarch64") "arm64" else it
}

val osName = System.getProperty("os.name")!!.let {
  if (it.startsWith("Windows")) "win32" else it
}


tasks {
  register<JavaExec>("demoBasic") {
    mainClass.set("danbroid.kipfs.demo.DemoBasic")
    classpath = sourceSets["main"].runtimeClasspath
  }
  register<JavaExec>("demoSubscribe") {
    mainClass.set("danbroid.kipfs.demo.DemoSubscribe")
    //classpath = sourceSets["main"].runtimeClasspath
    classpath = files("../jvm/libs/linux/amd64") + sourceSets["main"].runtimeClasspath

    //allJvmArgs.plusAssign("-Djava.library.path=${file("../jvm/libs/linux/amd64")}")
  }
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)

  val libName = "${osName.toLowerCase()}${arch.capitalizeAsciiOnly()}"
  implementation("com.github.danbrough.ipfs_mobile:$libName:_")

  val useLocalLibs = true //project.findProperty("localLibs") != null
  if (useLocalLibs) {
    implementation(project(":jvm"))
  } else {
    implementation("com.github.danbrough.ipfs_mobile:jvm:_")
  }
}




