package danbroid.ipfsmobile.test

import kipfs.cids.Cids
import kipfs.node.Node
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class CidTests {

  @Test
  fun cidTest() {
    log.info("cidTest()")
    val json = """{
      "name": "Wally",
      "age": 123 
    }"""

    val dag1 = Cids.dagCid(json)
    log.debug("dag1: $dag1")
    val expected = "bafyreigytojczarf4mjwpizi6r2xysikuzgyj7rkpud5ljxubeshddab7q"
    assert(dag1 == expected) {
      "dag1: $dag1 != expected: $expected"
    }
    val cborData = Cids.jsonToCbor(json)
    val dag2 = Cids.dagCidBytes(cborData,"cbor")

    log.debug("dag2: $dag2")
    assert(dag2 == expected) {
      "dag2: $dag2 != expected: $expected"
    }
  }


}