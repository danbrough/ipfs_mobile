package main

import (
  "flag"
  "kipfs/core"
  "kipfs/testing"
)

func main() {
  var url string
  flag.StringVar(&url, "url", "/ip4/127.0.0.1/tcp/5001", "url to connect to")
  flag.Parse()

  shell := core.NewShell(url)
  testing.TestLog.Trace("created shell %s", shell)

  //var req *core.RequestBuilder
  var resp []byte

  testing.TestLog.Trace("getting id..")
  var err error
  resp, err = shell.NewRequest("id").Send()
  if err != nil {
    panic(err)
  }
  testing.TestLog.Debug("got response: %s", string(resp))

}
