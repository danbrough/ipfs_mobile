package danbroid.kipfs.tests

import danbroid.logging.DBLog
import go.kipfs.core.Callback
import go.kipfs.core.Response
import go.kipfs.core.Shell
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.io.InputStream


object ConfigProperties {
  const val IPFS_URL = "IPFS_URL"
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class AbstractTests(private val log: DBLog) {

  private val ipfsUrl: String = System.getProperty(ConfigProperties.IPFS_URL) ?: let {
    "/ip4/127.0.0.1/tcp/5001"
  }

  private val shell: Shell by lazy {
    Shell(ipfsUrl)
  }

  @Test
  fun id() {
    shell.newRequest("id").send().decodeToString().also {
      log.debug("id: $it")
    }
  }

  @Test
  fun addString() {
    log.trace("addString()")

    shell.newRequest("add").postData3("Hello World".encodeToByteArray()).also {
      val result = it.input().readAllBytes().decodeToString()
      log.info("result: $result")
    }

    val callback = object : Callback {
      override fun onError(err: String) {
        log.error(err)
      }

      override fun onResponse(data: ByteArray) {
        log.debug("data: ${data.decodeToString()}")
      }

    }
    shell.newRequest("add").postData("Hello World".encodeToByteArray(), callback)
  }


  @Test
  fun addString2() {
    log.trace("addString2()")
    var wroteData = false


    shell.newRequest("add").post2("",)
  }
}


