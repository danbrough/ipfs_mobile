package danbroid.jvmlib.test

import danbroid.jvmlib.HelloWorld
import org.junit.Test

class Test {

  @Test
  fun test() {
    log.info("test()")
    log.info("the message is ${HelloWorld().stringFromJNI()}")
  }
}

private val log = danbroid.logging.configure("TEST", coloured = true)
