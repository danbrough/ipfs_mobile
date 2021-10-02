package danbroid.ipfsmobile.test

import kipfs.node.Node
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class Tests {

  companion object {

    private var _node: Node? = null
    private val node: Node
      get() = _node!!

    @JvmStatic
    @BeforeClass
    fun setup() {
      log.debug("setup()")
    }

    @JvmStatic
    @AfterClass
    fun tearDown() {
      log.debug("tearDown()")
    }
  }


  @Test
  fun test1() {
    log.info("test1()")

  }


}