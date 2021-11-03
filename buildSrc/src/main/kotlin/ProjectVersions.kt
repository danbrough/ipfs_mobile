import java.util.*
import org.gradle.api.JavaVersion

object ProjectVersions {
  const val SDK_VERSION = 31
  const val MIN_SDK_VERSION = 21
  const val BUILD_TOOLS_VERSION = "31.0.0"
  val JAVA_VERSION = JavaVersion.VERSION_1_8
  var VERSION_FORMAT = ""
  const val KOTLIN_JVM_VERSION = "1.8"
  val NDK_VERSION = if (System.getenv().containsKey("JITPACK")) "21.1.6352462" else "23.0.7599858"
  const val COMPOSE_VERSION = "1.1.0-alpha04"

  val JITPACK_BUILD = System.getenv().containsKey("JITPACK")
  var BUILD_VERSION = 1
  var VERSION_OFFSET = 1
  var GROUP_ID = "com.github.danbrough.ipfs_mobile"

  val VERSION_NAME: String
    get() = getVersionName()

  fun init(props: Properties) {
    BUILD_VERSION = props.getProperty("buildVersion", "1").toInt()
    VERSION_OFFSET = props.getProperty("versionOffset", "1").toInt()
    VERSION_FORMAT = props.getProperty("versionFormat", "0.0.%d").trim()
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
