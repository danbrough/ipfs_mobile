package core

import (
  "bytes"
  "context"
  files "github.com/ipfs/go-ipfs-files"
  "io"
  "io/ioutil"
  "kipfs/testing"
  "log"
  "strings"

  ipfsapi "github.com/ipfs/go-ipfs-api"
)

type Shell struct {
  ishell *ipfsapi.Shell
  url    string
}

func NewShell(url string) *Shell {
  return &Shell{
    ishell: ipfsapi.NewShell(url),
    url:    url,
  }
}

func (s *Shell) DagPut(data string) string {
  put, err := s.ishell.DagPut(data, "dag-json", "dag-cbor")
  if err != nil {
    log.Println(err)
    return ""
  }
  return put
}

func (s *Shell) Test() {
  rb := s.NewRequest("dag/put")
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

  //s.ishell.Request()
}

func (s *Shell) NewRequest(command string) *RequestBuilder {
  return &RequestBuilder{
    rb: s.ishell.Request(strings.TrimLeft(command, "/")),
  }
}

type RequestBuilder struct {
  rb *ipfsapi.RequestBuilder
}

func (req *RequestBuilder) Send() ([]byte, error) {
  res, err := req.rb.Send(context.Background())
  if err != nil {
    return nil, err
  }

  var doClose = func() {
    // testing.TestLog.Warn("Closing res")
    err := res.Close()
    if err != nil {
      testing.TestLog.Error("Error closing res: %s", err)
    }
  }

  defer doClose()

  if res.Error != nil {
    return nil, res.Error
  }

  return ioutil.ReadAll(res.Output)
}

func (req *RequestBuilder) Send2() (*ipfsapi.Response, error) {
  res, err := req.rb.Send(context.Background())
  if err != nil {
    return nil, err
  }

  if res.Error != nil {
    return nil, res.Error
  }

  return res, nil
}

func (req *RequestBuilder) Argument(arg string) {
  req.rb.Arguments(arg)
}

func (req *RequestBuilder) BoolOptions(key string, value bool) {
  req.rb.Option(key, value)
}

func (req *RequestBuilder) ByteOptions(key string, value []byte) {
  req.rb.Option(key, value)
}

func (req *RequestBuilder) StringOptions(key string, value string) {
  req.rb.Option(key, value)
}

func (req *RequestBuilder) BodyString(body string) {
  req.rb.BodyString(body)
}

func (req *RequestBuilder) BodyBytes(body []byte) {
  req.rb.BodyBytes(body)
}

func (req *RequestBuilder) PostData(data []byte) {
  req.post(data)
}

func (req *RequestBuilder) PostString(data string) {
  req.post(data)
}

func (req *RequestBuilder) PostReader(data Reader) {
  req.post(data)
}

func (req *RequestBuilder) post(data interface{}) {

  var r io.Reader
  switch data := data.(type) {
  case string:
    println("Its a string")
    r = strings.NewReader(data)
  case []byte:
    r = bytes.NewReader(data)
  case io.Reader:
    r = data
  }

  fr := files.NewReaderFile(r)
  slf := files.NewSliceDirectory([]files.DirEntry{files.FileEntry("", fr)})
  fileReader := files.NewMultiFileReader(slf, true)
  req.rb.Body(fileReader)

}

// Helpers

// NewUDSShell New unix socket domain shell
func NewUDSShell(sockpath string) *Shell {
  return NewShell("/unix/" + sockpath)
}

func NewTCPShell(port string) *Shell {
  return NewShell("/ip4/127.0.0.1/tcp/" + port)
}
