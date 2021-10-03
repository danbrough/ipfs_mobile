package danbroid.ipfsmobile.test

import android.net.Uri
import kipfs.core.Core
import kipfs.core.Node
import kipfs.core.Repo
import kipfs.core.Shell
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.io.File

class NodeTests {


  companion object {

    //whether to initialize the ipfs repo using internal storage or sdcard
    private const val useInternalStorage = false

    //directory name of the ipfs repo
    private const val repoName = "repo"

    //whether to delete any existing repo
    private const val deleteExistingRepo = false

    //set to 5001 (or whatever) to start the node with tcp support
    private val tcpPort: Int? = null

    private val repoDir: File by lazy {
      val filesDir = if (useInternalStorage)
        context.filesDir
      else
        context.getExternalFilesDir(null) ?: error("Failed to get external files dir")

      File(filesDir, repoName)
    }

    private val sockPath: String by lazy {
      log.error("CREATING SOCK PATH")
      Core.newSockManager(context.cacheDir.absolutePath).newSockPath()
    }

    private var varNode: Node? = null
    private val node: Node
      get() = varNode!!

    private var varRepo: Repo? = null
    private val repo: Repo
      get() = varRepo!!


    private var varShell: Shell? = null
    private val shell: Shell
      get() = varShell!!


    @JvmStatic
    @BeforeClass
    fun setup() {
      log.debug("setup()")
    }

    @JvmStatic
    @AfterClass
    fun tearDown() {
      log.debug("tearDown()")

      varNode?.close()?.also {
        log.trace("closed node")
        varNode = null
      }

      varRepo?.close()?.also {
        log.trace("closed repo")
        varRepo = null
      }

      varShell = null
    }
  }


  private fun runTestIf(cond: () -> Boolean, test: () -> Unit) {
    if (cond()) {
      runCatching {
        test()
      }.exceptionOrNull()?.also {
        log.error(it.message, it)
        throw it
      }
    }
  }

  @Test
  fun openRepo() = runTestIf({ varRepo == null }) {
    log.info("openRepo()")
    if (deleteExistingRepo && repoDir.exists()) {
      log.warn("deleting existing repo at $repoDir")
      repoDir.deleteRecursively() || error("Failed to delete existing repo at $repoDir")
    }

    if (!Core.repoIsInitialized(repoDir.absolutePath)) {
      log.info("repo is not initialized at $repoDir")
      if (!repoDir.mkdirs()) error("Failed to create dir $repoDir")

      //to initialize from existing initialConfig json
      //val config =Core.newConfig(initialConfig.encodeToByteArray())
      val config = Core.newDefaultConfig()
      log.trace("created config: $config")

      log.debug("initializing repo at $repoDir ...")
      Core.initRepo(repoDir.absolutePath, config)
      log.debug("opening repo..")
      varRepo = Core.openRepo(repoDir.absolutePath)
      log.trace("opened repo: $repo is initialized: ${Core.repoIsInitialized(repoDir.absolutePath)}")
    }
  }


  @Test
  fun startNode() = runTestIf({ varNode == null }) {
    openRepo()
    log.info("startNode()")
    log.debug("creating node .. will take 10 seconds or so ..")
    varNode = Core.newNode(repo)
    if (tcpPort != null) {
      log.debug("starting node on tcp port: $tcpPort")
      node.serveTCPAPI(tcpPort.toString())
    } else {

      //if [tcpPort] is null then this will be the path to the unix socket for the node
      log.debug("starting node with unix socket $sockPath")
      node.serveUnixSocketAPI(sockPath)
    }
    log.trace("node started")
    File(sockPath).parentFile!!.also {
      log.debug("parent of sock is $it exists: ${it.exists()}")
    }
  }

  @Test
  fun createShell() = runTestIf({ varShell == null }) {
    startNode()
    log.info("createShell()")
    varShell =
      if (tcpPort != null) {
        log.debug("creating new tcp shell to $tcpPort")
        Core.newTCPShell(tcpPort.toString())
      } else {
        log.debug("creating new UDS shell to $sockPath")
        Core.newUDSShell(sockPath)
      }
  }


  @Test
  fun cmdVersion() {
    createShell()
    log.info("cmdVersion()")
    shell.newRequest("version").send().decodeToString().also {
      log.debug("id: $it")
    }
  }


  @Test
  fun cmdDagPut() {
    createShell()
    log.info("dagPut()")

    val boundary = "${randomUUID()}${randomUUID()}"

    fun String.encodeString(): ByteArray = Charsets.ISO_8859_1.encode(this).array()

    fun CharSequence.uriEncode(allow: String? = null): String = if (allow != null) Uri.encode(
      this.toString(),
      allow
    ) else Uri.encode(this.toString())

    fun partHeader(
      fileName: String = "",
      isDirectory: Boolean,
      data: ByteArray? = null
    ): ByteArray {
      var headerData =
        ("\r\nContent-Disposition: form-data; name=\"file\"; filename=\"${fileName.uriEncode()}\"\r\n" +
            "Content-Type: ${if (isDirectory) "application/x-directory" else "application/octet-stream"}\r\n\r\n")
          .encodeString()
      if (!isDirectory)
        headerData += data!!
      headerData += "\r\n--$boundary".encodeString()
      return headerData
    }

    shell.newRequest("dag/put").also { requestBuilder ->


      requestBuilder.stringOptions("format", "dag-cbor")

      requestBuilder.header("Content-Type", "multipart/form-data; boundary=$boundary")
      var bodyBytes = "--$boundary".encodeString()

      bodyBytes += partHeader(
        "file",
        isDirectory = false,
        data = TestData.Wally.json.encodeString()
      )

      bodyBytes += "--\r\n\r\n".encodeString()

      log.trace("request: ${bodyBytes.decodeToString()}")


      requestBuilder.bodyBytes(bodyBytes)
    }.send().also {
      log.info("result: ${it.decodeToString()}")
    }
  }
}