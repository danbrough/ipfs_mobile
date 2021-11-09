import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly

plugins {
  kotlin("jvm")
  application
}

repositories {
  maven("https://h1.danbrough.org/maven")
}
/*System.getProperties().keys.forEach {
  println("$it -> ${System.getProperty(it?.toString())}")
}*/

val arch = System.getProperty("os.arch")!!
val osName = System.getProperty("os.name")!!.let {
  if (it.startsWith("Windows")) "win32" else it
}

println("arch is $arch")

application {
  mainClass.set("danbroid.kipfs.demo.Demo")
  applicationDefaultJvmArgs += "-Djava.library.path=${file("../jvm/libs/linux/$arch")}"
}

dependencies {
  implementation(AndroidUtils.logging)
  testImplementation(Testing.junit4)

  val libName = "${osName.toLowerCase()}${arch.capitalizeAsciiOnly()}"
  println("libName: $libName")
  implementation("com.github.danbrough.ipfs_mobile:$libName:_")
  implementation("com.github.danbrough.ipfs_mobile:core:_")
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")
}




