package main

import (
  "kipfs/cids"
  "kipfs/testing"
)

func main() {

  const json = `
  {
    "name": "Wally",
    "age" : 123 
  }
  `
  var dag1 = cids.DagCid(json)
  testing.TestLog.Info("dag1: %s", dag1)

  var data = cids.JsonToCbor(json)
  var dag2 = cids.DagCidBytes(data, "cbor")

  testing.TestLog.Debug("dag2: %s", dag2)
  var expected = "bafyreigytojczarf4mjwpizi6r2xysikuzgyj7rkpud5ljxubeshddab7q"
  if expected != dag2 {
    panic("expected: " + expected + " not " + dag2)
  }

}
