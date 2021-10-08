package main

import (
  "github.com/danbroid/ipfs_mobile/ipfs"
  "github.com/danbroid/ipfs_mobile/testing"
)

func main() {
  ipfs.StartIPFS(testing.TestLog)
}
