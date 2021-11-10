package danbroid.jni
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.util.*

object NativeLoader {
  @Suppress("UnsafeDynamicallyLoadedCode")
  fun loadLibrary(classLoader: ClassLoader, libName: String) {
    try {
      System.loadLibrary(libName)
    } catch (ex: UnsatisfiedLinkError) {
      val fullLibName = libFilename(libName)
      println("NativeLoader: trying classLoader.getResource($libName)")
      val url = classLoader.getResource(libFilename(libName))
      println("NativeLoader: url is $url")
      try {
        val file = Files.createTempFile("jni", libFilename(nameOnly(libName))).toFile()
        file.deleteOnExit()
        file.delete()
        assert(url != null)
        url!!.openStream().use { `in` -> Files.copy(`in`, file.toPath()) }
        System.load(file.canonicalPath)
      } catch (e: IOException) {
        throw UncheckedIOException(e)
      }
    }
  }

  private fun libFilename(libName: String): String {
    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
    if (osName.contains("win")) {
      return "$libName.dll"
    } else if (osName.contains("mac")) {
      return decorateLibraryName(libName, ".dylib")
    }
    return decorateLibraryName(libName, ".so")
  }

  private fun nameOnly(libName: String): String {
    val pos = libName.lastIndexOf('/')
    return if (pos >= 0) {
      libName.substring(pos + 1)
    } else libName
  }

  private fun decorateLibraryName(libraryName: String, suffix: String): String {
    if (libraryName.endsWith(suffix)) {
      return libraryName
    }
    val pos = libraryName.lastIndexOf('/')
    return if (pos >= 0) {
      libraryName.substring(0, pos + 1) + "lib" + libraryName.substring(pos + 1) + suffix
    } else {
      "lib$libraryName$suffix"
    }
  }
}