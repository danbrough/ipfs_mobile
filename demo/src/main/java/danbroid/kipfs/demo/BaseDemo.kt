package danbroid.kipfs.demo

import danbroid.kipfs.jni.NativeLoader
import go.kipfs.core.*
import java.io.File

open class BaseDemo {
  //http api port of the node
  private var shellUrl = System.getProperty("IPFS_URL") ?: "/ip4/127.0.0.1/tcp/5001"
  val nodePort = 5002

  init {
    log.error("nodeUrl: $shellUrl")
  }

  open var repoPath: File = File(System.getProperty("java.io.tmpdir"), "repo")

  private var _repo: Repo? = null
  private val repo: Repo by lazy {
    createRepo()
  }


  private var _node: Node? = null
  private val node: Node
    get() = createNode()


  private var _shell: Shell? = null
  val shell: Shell
    get() = createShell()

  open var offlineMode: Boolean = System.getProperty("IPFS_OFFLINE", "false").toBoolean().also { log.error("OFFLINE: $it")}
    set(value) {
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
    log.debug("offlineMode: $offlineMode")
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
    Core.newShell(shellUrl).also {
      _shell = it
    }
  }


  companion object {
    init {
      log.trace("loading library ...")
      //System.loadLibrary("gojni")/
      NativeLoader.loadLibrary(this::class.java.classLoader, "gojni")
      log.warn("library loaded")
    }
  }
}

