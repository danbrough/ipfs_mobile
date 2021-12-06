package main

import (
  "bufio"
  "flag"
  "fmt"
  "kipfs/core"
  "kipfs/testing"
  "os"
  "path/filepath"
  "time"
)

//whether to initialize the ipfs repo using internal storage or sdcard
const useInternalStorage = false

//directory name of the ipfs repo
const repoName = "repo"

//whether to delete any existing repo
const deleteExistingRepo = false

var repoPath = filepath.Join("/tmp", repoName)
var Log = testing.TestLog

func initRepo() {
  Log.Trace("initRepo()")
  if _, err := os.Stat(repoPath); os.IsNotExist(err) {
    Log.Warn("%s does not exist.", repoPath)
    err := os.MkdirAll(repoPath, os.ModePerm)
    if err != nil {
      panic(err)
    }
    Log.Trace("created %s", repoPath)
  }

  cfg, err := core.NewDefaultConfig()
  if err != nil {
    panic(err)
  }
  Log.Trace("created default config")
  err = core.InitRepo(repoPath, cfg)
  if err != nil {
    panic(err)
  }
  Log.Trace("initialized repo at %s", repoPath)
}

func pubMessage(topic string, msg string, shell *core.Shell) {
  Log.Info("pubMessage() topic: %s message: %s", topic, msg)
  var req *core.RequestBuilder
  req = shell.NewRequest("pubsub/pub")
  req.Argument(topic)
  req.Argument(msg)
  response, err := req.Send()
  if err != nil {
    panic(err)
  }
  Log.Debug("response: %s", response)
}

func publish(topic string, shell *core.Shell) {
  var count = 0
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
  time.Sleep(1 * time.Second)
  pubMessage(topic, fmt.Sprintf("Message: %d", count), shell)
  count++
}

func subscribe(topic string, shell *core.Shell) {
  Log.Info("topic: %s", topic)
  var req *core.RequestBuilder
  time.Sleep(time.Second)
  /* time.Sleep(10 * time.Second)
  req = shell.NewRequest("pubsub/pub")
  req.Argument(topic)
  req.Argument("Hello from kipfs!")
  //req.BoolOptions("discover", true)
  Log.Trace("sending pub ..")
  resp, err := req.Send()
  if err != nil {
    panic(err)
  }
  Log.Info("pub response: %s", string(resp))

  time.Sleep(2 * time.Second)

  */
  req = shell.NewRequest("pubsub/sub")
  req.Argument(topic)
  Log.Trace("subscribing to %s", topic)
  response, err := req.Send2()
  if err != nil {
    panic(err)
  }

  var doClose = func() {
    testing.TestLog.Warn("Closing response")
    err := response.Close()
    if err != nil {
      testing.TestLog.Error("Error closing response: %s", err)
    }
  }

  defer doClose()

  Log.Trace("reading response %s", response)
  scanner := bufio.NewScanner(response.Output)
  // optionally, resize scanner's capacity for lines over 64K, see next example
  for scanner.Scan() {
    Log.Info("response line: %s", scanner.Text())
  }

  if err := scanner.Err(); err != nil {
    Log.Error("failed reading response: %s", err)
  }

}

func main() {
  var offline bool
  flag.BoolVar(&offline, "offline", false, "run node offline")
  var dagToGet string
  flag.StringVar(&dagToGet, "dag", "", "dag to retrieve")
  var topic string
  var pubTopic string
  flag.StringVar(&topic, "subscribe", "", "pubsub topic to subscribe to")
  flag.StringVar(&pubTopic, "publish", "", "pubsub topic to publish to")
  flag.Parse()

  Log.Info("running demo.. offline: %t", offline)

  if !core.RepoIsInitialized(repoPath) {
    Log.Info("%s is not initialized", repoPath)
    initRepo()
  } else {
    Log.Warn("using existing repo at %s", repoPath)
  }

  repo, err := core.OpenRepo(repoPath)
  if err != nil {
    panic(err)
  }
  Log.Debug("opened repo %s", repo)

  Log.Trace("creating node ..")
  node, err := core.NewNode(repo, !offline)
  if err != nil {
    panic(err)
  }
  Log.Debug("created node %s", node)

  var port = "5002"
  Log.Trace("starting node with port %s", port)
  _, err = node.ServeTCPAPI(port)
  if err != nil {
    panic(err)
  }
  Log.Info("node running on %s. creating shell..", port)

  shell := core.NewShell("/ip4/192.168.1.4/tcp/5001")
  Log.Trace("created shell %s", shell)

  if topic != "" {
    subscribe(topic, shell)
    return
  }

  if pubTopic != "" {
    publish(pubTopic, shell)
    return
  }

  var req *core.RequestBuilder
  var resp []byte

  if dagToGet != "" {
    Log.Debug("call dag get %s", dagToGet)
    req = shell.NewRequest("dag/get")
    req.Argument(dagToGet)
    resp, err = req.Send()
    if err != nil {
      Log.Error("dag/get failed: %s", err)
      if offline {
        Log.Warn("Try running without the -offline flag")
      }
    } else {
      //should be "Hello World"
      Log.Debug("got dag/get response: %s", string(resp))
    }
    return
  }

  Log.Trace("getting id..")
  resp, err = shell.NewRequest("id").Send()
  if err != nil {
    panic(err)
  }
  Log.Debug("got response: %s", string(resp))

  req = shell.NewRequest("dag/get")
  req.Argument("bafyreidfq7gnjnpi7hllpwowrphojoy6hgdgrsgitbnbpty6f2yirqhkom")
  Log.Trace("getting dag: bafyreidfq7gnjnpi7hllpwowrphojoy6hgdgrsgitbnbpty6f2yirqhkom")

  resp, err = req.Send()
  if err != nil {
    Log.Error("dag/get failed: %s", err)
    if offline {
      Log.Warn("Try running without the -offline flag")
    }
  } else {
    //should be "Hello World"
    Log.Debug("got dag/get response: %s", string(resp))

  }

  Log.Trace("calling cat QmVdiu6wH89Cg6rcQZHidJqxQAeRktSVGP2QUGqghaxUsp")
  req = shell.NewRequest("cat")
  req.Argument("QmVdiu6wH89Cg6rcQZHidJqxQAeRktSVGP2QUGqghaxUsp")
  resp, err = req.Send()
  if err != nil {
    Log.Error("cat QmVdiu6wH89Cg6rcQZHidJqxQAeRktSVGP2QUGqghaxUsp failed: %s", err)
    if offline {
      Log.Warn("Try running without the -offline flag")
    }
  } else {

    //should be "Hello World"
    Log.Debug("got cat response: %s", string(resp))
  }

  if !offline {
    Log.Warn("You should now be able to run this with the -offline flag")
  }

  /*  err = node.Close()
      if err != nil {
        panic(err)
      }
      err = repo.Close()
      if err != nil {
        panic(err)
      }*/

  /*  err = node.Close()
      if err != nil {
        panic(err)
      }*/

}
