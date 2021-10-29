package danbroid.kipfs.jvm

import go.kipfs.core.*
import java.io.File

open class Demo {
  //http api port of the node
  var nodePort = 5002

  open var repoPath: File = File("/tmp/repo")

  private var _repo: Repo? = null
  val repo: Repo by lazy {
    createRepo()
  }


  private var _node: Node? = null
  val node: Node
    get() = createNode()


  private var _shell: Shell? = null
  val shell: Shell
    get() = createShell()

  open var offlineMode: Boolean = false
    set(value) {
      log.error("SETTING OFFLINE to $offlineMode")
      if (field != value) {
        field = value
        _shell = null
        _node?.close()?.also {
          _node = null
        }
      }
    }

  open val config: Config by lazy {
    Core.newDefaultConfig()
  }

  open fun createRepo(): Repo = _repo ?: run {
    if (_repo != null) return _repo!!
    log.warn("creating repo at ${repoPath.absolutePath}")
    if (!Core.repoIsInitialized(repoPath.absolutePath)) {
      log.warn("initializing repo at ${repoPath.absolutePath} ..")
      if (!repoPath.mkdirs()) error("Failed to created ${repoPath.absolutePath}")
      val cfg = config
      log.trace("GOT CONFIG: $config")
      Core.initRepo(repoPath.absolutePath, cfg)
    } else {
      log.info("using existing repo at ${repoPath.absolutePath}")
    }

    Core.openRepo(repoPath.absolutePath).also {
      _repo = it
    }
  }

  open fun createNode(): Node = _node ?: Core.newNode(repo, !offlineMode).also {
    log.info("starting node on $nodePort")
    it.serveTCPAPI(nodePort.toString())
    _node = it
  }

  open fun createShell(): Shell = _shell ?: run {
    node
    Core.newTCPShell(nodePort.toString()).also {
      _shell = it
    }
  }

  fun run() {
    log.info("run()")

    shell.newRequest("id").send().decodeToString().also {
      log.trace("RESPONSE: $it")
    }
  }

  companion object {

    val log = danbroid.logging.configure("TEST", coloured = true)

    @JvmStatic
    fun main(args: Array<String>) {
      log.debug("main()")
      System.loadLibrary("gojni")

      Demo().run()

    }
  }
}
