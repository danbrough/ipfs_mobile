package danbroid.jvmlib.test

import org.junit.Test

class Test : Tests() {

  @Test
  fun test() {
    log.info("test()")

    /*   shell.newRequest("id").send().decodeToString().also {
         log.info("id: $it")
       }*/
  }
}

private val log = danbroid.logging.configure("TEST", coloured = true)
