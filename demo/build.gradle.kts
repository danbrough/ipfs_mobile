import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly




plugins {
  kotlin("jvm")
  application
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

println("arch is $arch")



application {
  mainClass.set("danbroid.kipfs.demo.Demo")
}


dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)

  val libName = "${osName.toLowerCase()}${arch.capitalizeAsciiOnly()}"
  println("libName: $libName")
  implementation("com.github.danbrough.ipfs_mobile:$libName:_")
  project.findProperty("localLibs")?.also {
    println("using local libs ..")

    implementation(project(":core"))
    implementation(project(":jvm"))

  } ?: run {
    implementation("com.github.danbrough.ipfs_mobile:core:_")
    implementation("com.github.danbrough.ipfs_mobile:jvm:_")
  }
}




