package main

import (
  "kipfs/ipfs"
  "kipfs/testing"
)

func main() {
  ipfs.StartIPFS(testing.TestLog)
}
