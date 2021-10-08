package api

import (
  "github.com/danbroid/ipfs_mobile/ipfs"
  "github.com/danbroid/ipfs_mobile/misc"
)

func StartIPFS(sink misc.LogSink) {
  ipfs.StartIPFS(createLog(sink))
}
