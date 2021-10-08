package api

import "github.com/danbroid/ipfs_mobile/kipfs_go/misc"

func createLog(sink misc.LogSink) misc.Log {
  return misc.Logger{Name: "KIPFS_GO", Skip: 2, LogSink: sink}
}
