package core

import (
  "context"
  "io/ioutil"
  "kipfs/testing"
  "log"
  "strings"

  ipfs_api "github.com/ipfs/go-ipfs-api"
)

type Shell struct {
  ishell *ipfs_api.Shell
  url    string
}

func NewShell(url string) *Shell {
  return &Shell{
    ishell: ipfs_api.NewShell(url),
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

  //s.ishell.Request()
}

func (s *Shell) NewRequest(command string) *RequestBuilder {
  return &RequestBuilder{
    rb: s.ishell.Request(strings.TrimLeft(command, "/")),
  }
}

type RequestBuilder struct {
  rb *ipfs_api.RequestBuilder
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

func (req *RequestBuilder) Send2() (*ipfs_api.Response, error) {
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

func (req *RequestBuilder) Header(name, value string) {
  req.rb.Header(name, value)
}

// Helpers

// New unix socket domain shell
func NewUDSShell(sockpath string) *Shell {
  return NewShell("/unix/" + sockpath)
}

func NewTCPShell(port string) *Shell {
  return NewShell("/ip4/127.0.0.1/tcp/" + port)
}
