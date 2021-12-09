package core

import "io"

type Reader interface {
  Read(p []byte) (n int, err error)
}

type JReader interface {
  io.Reader
}
