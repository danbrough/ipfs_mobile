package core

import (
  "io"
  "kipfs/testing"
  "os"
)

var EOF = io.EOF

type Reader = io.Reader
type Closer = io.Closer
type ReadCloser = io.ReadCloser

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

type KReader interface {
  io.Reader
}
