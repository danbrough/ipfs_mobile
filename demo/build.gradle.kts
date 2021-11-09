import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeAsciiOnly

plugins {
  kotlin("jvm")
  application
}

val arch = System.getProperty("os.arch")!!
val osName = System.getProperty("os.name")!!

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
  implementation("com.github.danbrough.ipfs_mobile:jvm:_")
}




