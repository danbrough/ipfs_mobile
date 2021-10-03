package main

import "kipfs/testing"

//whether to initialize the ipfs repo using internal storage or sdcard
const useInternalStorage = false

//directory name of the ipfs repo
const repoName = "repo"

//whether to delete any existing repo
const deleteExistingRepo = false

//set to 5001 (or whatever) to start the node with tcp support
const tcpPort = -1

func main() {
  testing.TestLog.Info("%s: calling testlog ..", "prefix")
}
