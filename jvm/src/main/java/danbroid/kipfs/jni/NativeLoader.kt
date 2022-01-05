package danbroid.kipfs.jni

import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.util.*

object NativeLoader {
  private val log = danbroid.logging.getLog(this::class)

  @Suppress("UnsafeDynamicallyLoadedCode")
  fun loadLibrary(classLoader: ClassLoader, libName: String = "gojni") {
    try {
      System.loadLibrary(libName)
    } catch (ex: UnsatisfiedLinkError) {
      val fullLibName = libFilename(libName)
      log.trace("NativeLoader!: trying classLoader.getResource($fullLibName)")
      val url = classLoader.getResource(fullLibName)

      log.trace("NativeLoader: url is $url")
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
      return "lib$libName.dll"
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