package main

import (
  "flag"
  "fmt"
  "kipfs/core"
  "kipfs/testing"
)

func getID(shell *core.Shell) {
  var resp []byte

  testing.TestLog.Trace("getting id..")
  var err error
  resp, err = shell.NewRequest("id").Send()
  if err != nil {
    panic(err)
  }
  testing.TestLog.Debug("got response: %s", string(resp))
}

func dagPut(shell *core.Shell) {

  rb := shell.NewRequest("dag/put")
  rb.StringOptions("input-codec", "dag-json")
  rb.StringOptions("store-codec", "dag-cbor")

  rb.PostString(`"Hello World"`)
  println("Sending request ..")
  resp, err := rb.Send()
  if err != nil {
    println("Error", err)
  } else {
    println("Response:", string(resp))
  }

  if err != nil {
    fmt.Printf("Failed: %s", err.Error())
  }
  testing.TestLog.Debug("got response: %s", string(resp))
}

func main() {
  var url string
  flag.StringVar(&url, "url", "/ip4/127.0.0.1/tcp/5001", "url to connect to")
  flag.Parse()

  shell := core.NewShell(url)
  testing.TestLog.Trace("created shell %s", shell)

  shell.Test()

  //testing.TestLog.Info(shell.DagPut(`"Hello World"`))

  //dagPut(shell)

  //var req *core.RequestBuilder

}
