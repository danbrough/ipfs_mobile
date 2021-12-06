package danbroid.jvmlib.test

import danbroid.kipfs.jni.NativeLoader
import org.junit.Test

class Test : Tests() {

  companion object {
    init {
      try {
        System.loadLibrary("libgojni")
        log.info("loaded it")
      } catch (err: Throwable) {
        log.error(err.message, err)
      }
      try {
        System.loadLibrary("gojni")
        log.info("loaded it @")
      } catch (err: Throwable) {
        log.error(err.message, err)
      }
      //NativeLoader.loadLibrary(Test::class.java.classLoader, "libgojni")
    }
  }

  @Test
  fun test() {
    log.info("test()")
    shell.newRequest("id").send().decodeToString().also {
      log.info("id: $it")
    }
  }
}

private val log = danbroid.logging.configure("TEST", coloured = true)
