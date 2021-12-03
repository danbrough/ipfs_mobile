package danbroid.ipfsmobile.test

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import danbroid.logging.DBLog
import go.kipfs.core.*
import java.io.File
import java.util.*


private val logger = danbroid.logging.configure("TEST", coloured = true)


open class Tests() {


  //http api port of the node
  var nodePort = 5002

  open var repoPath: File = File(context.filesDir, "kipfs/repo")

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

  companion object {
    val log: DBLog = logger
    val context: Context = InstrumentationRegistry.getInstrumentation().context

    init {
      log.warn("loading libgojni ..")
      System.load("libgojni")
    }
  }

  open fun createRepo(): Repo = _repo ?: run {
    if (_repo != null) return _repo!!
    log.warn("creating repo at ${repoPath.absolutePath}")
    if (!Core.repoIsInitialized(repoPath.absolutePath)) {
      log.warn("initializing repo at ${repoPath.absolutePath} ..")
      if (!repoPath.mkdirs()) error("Failed to created ${repoPath.absolutePath}")
      Core.initRepo(repoPath.absolutePath, config)
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

  fun randomUUID() = UUID.randomUUID().toString()
}





