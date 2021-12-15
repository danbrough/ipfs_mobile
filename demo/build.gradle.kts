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


fun TaskContainerScope.registerDemo(name:String,cls:String) =
  register<JavaExec>(name) {
    mainClass.set(cls)
    classpath = files("../jvm/libs/linux/libs/amd64") + sourceSets["main"].runtimeClasspath
    val execTask = this

    project.properties.keys.forEach { key->
      if (key.startsWith("IPFS_")) {
        execTask.systemProperties[key] = project.property(key).toString()
      }
    }
  }

tasks {
  registerDemo("basic","danbroid.kipfs.demo.DemoBasic")
  registerDemo("subscribe","danbroid.kipfs.demo.DemoSubscribe")
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




