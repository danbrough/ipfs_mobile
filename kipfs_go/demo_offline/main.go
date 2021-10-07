package main

import (
  "kipfs/core"
  "kipfs/testing"
  "os"
  "path/filepath"
)

//whether to initialize the ipfs repo using internal storage or sdcard
const useInternalStorage = false

//directory name of the ipfs repo
const repoName = "repo"

//whether to delete any existing repo
const deleteExistingRepo = false

var repoPath = filepath.Join("/tmp", repoName)
var log = testing.TestLog

func initRepo() {
  log.Trace("initRepo()")
  if _, err := os.Stat(repoPath); os.IsNotExist(err) {
    log.Warn("%s does not exist.", repoPath)
    err := os.MkdirAll(repoPath, os.ModePerm)
    if err != nil {
      panic(err)
    }
    log.Trace("created %s", repoPath)
  }

  cfg, err := core.NewDefaultConfig()
  if err != nil {
    panic(err)
  }
  log.Trace("created default config")
  err = core.InitRepo(repoPath, cfg)
  if err != nil {
    panic(err)
  }
  log.Trace("initialized repo at %s", repoPath)
}

func main() {
  log.Info("running demo..")

  if !core.RepoIsInitialized(repoPath) {
    log.Info("%s is not initialized", repoPath)
    initRepo()
  } else {
    log.Warn("using existing repo at %s", repoPath)
  }

  //var repo = core.Repo{}

  repo, err := core.OpenRepo(repoPath)
  if err != nil {
    panic(err)
  }
  log.Debug("opened repo %s", repo)

  log.Trace("creating node ..")
  node, err := core.NewNode(repo, false)
  if err != nil {
    panic(err)
  }
  log.Debug("created node %s", node)

  var port = "5001"
  log.Trace("starting node with port %s", port)
  _, err = node.ServeTCPAPI(port)
  if err != nil {
    panic(err)
  }
  log.Info("node running on %s. creating shell..", port)

  shell := core.NewTCPShell(port)
  log.Trace("created shell %s", shell)
  if err != nil {
    panic(err)
  }
  /*log.Trace("getting id..")
    resp, err := shell.NewRequest("id").Send()
    if err != nil {
      panic(err)
    }
    log.Trace("got response: %s", string(resp))
  */

  req := shell.NewRequest("cat")
  req.Argument("QmVdiu6wH89Cg6rcQZHidJqxQAeRktSVGP2QUGqghaxUsp")
  resp, err := req.Send()
  log.Trace("got cat response: %s", string(resp))

  /*req := shell.NewRequest("dag/get")
    req.Argument("bafyreidfq7gnjnpi7hllpwowrphojoy6hgdgrsgitbnbpty6f2yirqhkom")
    resp, err := req.Send()
    log.Trace("got dag/get response: %s", string(resp))*/

  /*  err = node.Close()
      if err != nil {
        panic(err)
      }
      err = repo.Close()
      if err != nil {
        panic(err)
      }*/
}
