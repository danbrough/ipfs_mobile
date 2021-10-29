package danbroid.jni

class Utils {
  private fun normalize(value: String?) =
    value?.lowercase()?.replace("[^a-z0-9]+".toRegex(), "") ?: ""

  private fun normalizeArch(arch: String): String? {
    val value = normalize(arch)

    if (value.matches("^(x8664|amd64|ia32e|em64t|x64)$".toRegex())) {
      return "x86_64"
    }
    if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$".toRegex())) {
      return "x86_32"
    }
    if (value.matches("^(ia64w?|itanium64)$".toRegex())) {
      return "itanium_64"
    }
    if ("ia64n" == value) {
      return "itanium_32"
    }
    if (value.matches("^(sparc|sparc32)$".toRegex())) {
      return "sparc_32"
    }
    if (value.matches("^(sparcv9|sparc64)$".toRegex())) {
      return "sparc_64"
    }
    if (value.matches("^(arm|arm32)$".toRegex())) {
      return "arm_32"
    }
    if ("aarch64" == value) {
      return "aarch_64"
    }
    if (value.matches("^(mips|mips32)$".toRegex())) {
      return "mips_32"
    }
    if (value.matches("^(mipsel|mips32el)$".toRegex())) {
      return "mipsel_32"
    }
    if ("mips64" == value) {
      return "mips_64"
    }
    if ("mips64el" == value) {
      return "mipsel_64"
    }
    if (value.matches("^(ppc|ppc32)$".toRegex())) {
      return "ppc_32"
    }
    if (value.matches("^(ppcle|ppc32le)$".toRegex())) {
      return "ppcle_32"
    }
    if ("ppc64" == value) {
      return "ppc_64"
    }
    if ("ppc64le" == value) {
      return "ppcle_64"
    }
    if ("s390" == value) {
      return "s390_32"
    }
    if ("s390x" == value) {
      return "s390_64"
    }
    if ("riscv" == value) {
      return "riscv"
    }
    return if ("e2k" == value) {
      "e2k"
    } else null
  }

  private fun normalizeOs(os: String): String? {
    val value = normalize(os)
    if (value.startsWith("aix")) {
      return "aix"
    }
    if (value.startsWith("hpux")) {
      return "hpux"
    }
    if (value.startsWith("os400")) {
      // Avoid the names such as os4000
      if (value.length <= 5 || !Character.isDigit(value[5])) {
        return "os400"
      }
    }
    if (value.startsWith("linux")) {
      return "linux"
    }
    if (value.startsWith("macosx") || value.startsWith("osx")) {
      return "osx"
    }
    if (value.startsWith("freebsd")) {
      return "freebsd"
    }
    if (value.startsWith("openbsd")) {
      return "openbsd"
    }
    if (value.startsWith("netbsd")) {
      return "netbsd"
    }
    if (value.startsWith("solaris") || value.startsWith("sunos")) {
      return "sunos"
    }
    if (value.startsWith("windows")) {
      return "windows"
    }
    return if (value.startsWith("zos")) {
      "zos"
    } else null
  }
}