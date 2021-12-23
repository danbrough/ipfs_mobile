package core

import (
  "io"
  "kipfs/testing"
  "os"
)

type Reader interface {
  io.Reader
}

func WriteStuff(data []byte, path string) {
  testing.TestLog.Info("Writing stuff to %s", path)
  err := os.WriteFile(path, data, 0644)
  if err != nil {
    panic(err)
  }

}

type Callback interface {
  OnResponse(data []byte)
  OnError(err string)
}
