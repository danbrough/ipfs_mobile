package api

import (
  "kipfs/ipfs"
  "kipfs/misc"
)

func StartIPFS(sink misc.LogSink) {
  ipfs.StartIPFS(createLog(sink))
}
