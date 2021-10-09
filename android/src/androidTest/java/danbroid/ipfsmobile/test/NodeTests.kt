package danbroid.ipfsmobile.test

import org.junit.Test

class NodeTests : Tests() {
  @Test
  fun test() {
    log.debug("getting id ...")
    log.debug("id response: ${shell.newRequest("id").send().decodeToString()}")
  }

}