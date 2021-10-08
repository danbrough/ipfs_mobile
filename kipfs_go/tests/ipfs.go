package main

import (
  "github.com/danbroid/ipfs_mobile/kipfs_go/ipfs"
  "github.com/danbroid/ipfs_mobile/kipfs_go/testing"
)

func main() {
  ipfs.StartIPFS(testing.TestLog)
}
