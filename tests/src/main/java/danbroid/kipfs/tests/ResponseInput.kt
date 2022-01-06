package danbroid.kipfs.tests

import go.kipfs.core.Response
import java.io.InputStream

class ResponseInput(private val response: Response, bufSize: Int = 1024) :
  InputStream() {

  private val buf = ByteArray(bufSize)
  private var readCount = 0
  private var readIndex = 0


  override fun close() {
    super.close()
    response.close()
  }

  override fun read(): Int {

    if (readCount == -1) return -1

    if (readCount > 0 && readIndex == readCount) {
      readCount = 0
      readIndex = 0
    }

    if (readCount == 0) {
      readCount = response.read(buf).toInt()
      if (readCount == -1) {
        return -1
      }
      readIndex = 0
    }

    return buf[readIndex++].toInt()
  }
}

fun Response.input(): InputStream = ResponseInput(this)