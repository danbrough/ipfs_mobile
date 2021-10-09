package danbroid.ipfsmobile.test

import go.kipfs.cids.Cids
import org.junit.Test

class CidTests : Tests(){

  @Test
  fun cidTest() {
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


}