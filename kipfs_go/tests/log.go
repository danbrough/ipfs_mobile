package main

import (
  "github.com/danbroid/ipfs_mobile/kipfs_go/misc"
  "github.com/danbroid/ipfs_mobile/kipfs_go/testing"
)

func logTest(l misc.Log) {
  l.Trace("trace [%s]", "A message")
  l.Debug("debug")
  l.Info("info")
  l.Warn("warn")
  l.Error("error")
}

func main() {
  testing.TestLog.Info("%s: calling testlog ..", "prefix")
  logTest(testing.TestLog)

}
