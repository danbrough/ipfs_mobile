package main

import (
  "github.com/danbrough/ipfs_mobile/kipfs_go/ipfs"
  "github.com/danbrough/ipfs_mobile/kipfs_go/testing"
)

func main() {
  ipfs.StartIPFS(testing.TestLog)
}
