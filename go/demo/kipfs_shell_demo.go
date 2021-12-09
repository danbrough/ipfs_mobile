package main

import (
  "flag"
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

  var data = []byte("\"Hello World\"")
  testing.TestLog.Trace("dagPut() " + string(data))
  var err error
  resp := shell.NewRequest("dag/put")
  //input-codec=dag-json&store-codec=dag-cbor&stream-channels=true
  resp.StringOptions("encoding", "json")
  resp.StringOptions("input-codec", "dag-json")
  resp.StringOptions("store-codec", "dag-cbor")
  resp.Header("Transfer-Encoding", "chunked")

  resp.BodyString(`"Hello World"`)
  var respData []byte
  respData, err = resp.Send()
  if err != nil {
    panic(err)
  }
  testing.TestLog.Debug("got response: %s", string(respData))
}

func main() {
  var url string
  flag.StringVar(&url, "url", "/ip4/127.0.0.1/tcp/5001", "url to connect to")
  flag.Parse()

  shell := core.NewShell(url)
  testing.TestLog.Trace("created shell %s", shell)

  shell.Test()
  //testing.TestLog.Info(shell.DagPut(`"Hello World"`))

  dagPut(shell)

  //var req *core.RequestBuilder

}
