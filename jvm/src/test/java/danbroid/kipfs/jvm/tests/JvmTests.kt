package danbroid.kipfs.jvm.tests

import danbroid.kipfs.jni.NativeLoader
import danbroid.kipfs.tests.AbstractTests
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

private val log = danbroid.logging.configure("TESTS", coloured = true)

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class JvmTests : AbstractTests(log) {
  companion object {

    init {
      log.info("loading native library..")
      NativeLoader.loadLibrary(JvmTests::class.java.classLoader)
    }
  }


}