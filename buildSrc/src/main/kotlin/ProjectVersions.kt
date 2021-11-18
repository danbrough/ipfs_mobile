import java.util.*
import org.gradle.api.JavaVersion
import org.gradle.api.Project

object ProjectVersions {
  const val SDK_VERSION = 31
  const val MIN_SDK_VERSION = 21
  const val BUILD_TOOLS_VERSION = "31.0.0"
  val JAVA_VERSION = JavaVersion.VERSION_1_8
  var VERSION_FORMAT = "0.0.1-%02d"
  const val KOTLIN_JVM_VERSION = "1.8"

  val JITPACK_BUILD = System.getenv().containsKey("JITPACK")
  val NDK_VERSION = if (JITPACK_BUILD) "21.1.6352462" else "23.0.7599858"
  var BUILD_VERSION = 1
  var VERSION_OFFSET = 1
  lateinit var MAVEN_REPO: String

  var GROUP_ID = "com.github.danbrough.ipfs_mobile"

  val VERSION_NAME: String
    get() = getVersionName()

  fun init(project: Project, props: Properties) {
    BUILD_VERSION = props.getProperty("buildVersion", "1").toInt()
    VERSION_OFFSET = props.getProperty("versionOffset", "1").toInt()
    VERSION_FORMAT = props.getProperty("versionFormat", "0.0.%d").trim()
    MAVEN_REPO = project.findProperty("LOCAL_MAVEN_REPO")?.toString()?.trim()
      ?: "https://h1.danbrough.org/maven"
  }

  fun getIncrementedVersionName() = getVersionName(BUILD_VERSION + 1)


  fun getVersionName(version: Int = BUILD_VERSION) =
    VERSION_FORMAT.format(version - VERSION_OFFSET)


}

object AndroidUtils {
  private const val version = "_"
  private const val group = "com.github.danbrough.androidutils"

  const val misc = "$group:misc:$version"
  const val compose = "$group:compose:$version"
  const val permissions = "$group:permissions:$version"
  const val menu = "$group:menu:$version"
  const val logging = "$group:logging:$version"


}
