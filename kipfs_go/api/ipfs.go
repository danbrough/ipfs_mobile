package api

import (
  "github.com/danbroid/ipfs_mobile/kipfs_go/ipfs"
  "github.com/danbroid/ipfs_mobile/kipfs_go/misc"
)

func StartIPFS(sink misc.LogSink) {
  ipfs.StartIPFS(createLog(sink))
}
