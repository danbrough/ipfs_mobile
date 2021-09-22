package main

import (
  "kipfs/misc"
  "kipfs/testing"
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
