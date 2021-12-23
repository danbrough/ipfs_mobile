package danbroid.kipfs.demo

import danbroid.kipfs.jvm.TestData
import go.kipfs.cids.Cids
import go.kipfs.core.Callback
import go.kipfs.core.Core
import go.kipfs.files.DirEntry
import java.nio.charset.Charset
import kotlin.io.path.Path
import kotlin.io.path.writeBytes

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

  private fun addData() {
    log.debug("addData()")
    val data = (0 until 256).map { it.toByte() }.toByteArray()


    shell.newRequest("add").also { request ->
      request.postData(data, object : Callback {
        override fun onError(err: String?) {
          log.error(err)
        }

        override fun onResponse(data: ByteArray?) {
          log.info("response: ${data?.decodeToString()}")
        }

      })
    }
  }

  private fun dagPut() {
    log.debug("dagPut()")
    shell.newRequest("dag/put").also { request ->
      request.stringOptions("store-codec", "dag-cbor")
      request.stringOptions("input-codec", "dag-json")
      request.boolOptions("pin", true)
      request.postData("\"Hello World\"".encodeToByteArray(), object : Callback {
        override fun onError(err: String?) {
          log.error(err)
        }

        override fun onResponse(data: ByteArray?) {
          log.info("response: ${data?.decodeToString()}")
        }

      })
    }

  }

  private fun filesWrite() {
    log.debug("filesWrite()")

    val data = (0 until 256).map { it.toByte() }.toByteArray()
    Path("/tmp/test1.bin").writeBytes(data)


    //go.kipfs.files.MultiFileReader(File("/tmp/test"), true)

    shell.newRequest("files/write").also { request ->
      log.trace("request created")
      request.argument("/test/test4.bin")
      request.boolOptions("create", true)
      request.boolOptions("parents", true)
      request.boolOptions("truncate", true)
      request.postData(data, object : Callback {
        override fun onError(err: String?) {
          log.error(err)
        }

        override fun onResponse(data: ByteArray?) {
          log.info("response: ${data?.decodeToString()}")
        }

      })
    }
  }


  private fun filesWrite2() {
    log.debug("filesWrite2()")
  }


  fun run() {
    log.info("run(): offlineMode:$offlineMode")

    addData()
    /*dagPut()
    filesWrite()*/


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

