package danbroid.kipfs.demo

import danbroid.kipfs.jvm.TestData
import go.kipfs.cids.Cids
import java.util.*

class DemoBasic : BaseDemo() {


  private fun cidTest() {
    log.info("cidTest()")

    val dag1 = Cids.dagCid(TestData.Wally.json)
    log.debug("dag1: $dag1")
    val expected = TestData.Wally.cid
    assert(dag1 == expected) {
      "dag1: $dag1 != expected: $expected"
    }
    val cborData = Cids.jsonToCbor(TestData.Wally.json)
    val dag2 = Cids.dagCidBytes(cborData, "cbor")

    log.debug("dag2: $dag2")
    assert(dag2 == expected) {
      "dag2: $dag2 != expected: $expected"
    }
  }

  private fun dagTest() {
    log.debug("dagTest()")

    val dag = "bafyreidfq7gnjnpi7hllpwowrphojoy6hgdgrsgitbnbpty6f2yirqhkom"
    log.trace("looking up dag: $dag")
    shell.newRequest("dag/get").also {

      it.send().decodeToString().also { data ->
        log.info("response: $data")
      }
    }
  }

  fun dagGet(dag: String) {
    log.debug("dagGet() $dag")
    shell.newRequest("dag/get").also {
      it.argument(dag)
      it.send().decodeToString().also { data ->
        log.info("response: $data")
      }
    }
  }

  private fun dagPut(data: String) {
    log.debug("dagPut() $data")
    shell.dagPut(data).also {
      log.info("response: $it")
    }
  }

  fun run() {
    log.info("run(): offlineMode:$offlineMode")

    dagPut("\"Hello World\"")
    /*shell.newRequest("id").send().decodeToString().also {
      log.trace("RESPONSE: $it")
    }

    cidTest()

    dagTest()*/
  }

  companion object {


    @JvmStatic
    fun main(args: Array<String>) {
      log.debug("main()")

      val demo = DemoBasic()

      demo.run()
      args.asList().forEach {
        log.debug("getting dag $it")
        demo.dagGet(it)
      }
    }
  }
}

