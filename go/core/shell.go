package core

import (
  "bytes"
  "context"
  "fmt"
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
    fmt.Printf("Error: %s", err.Error())
  } else {
    println("Response:", string(resp))
  }

  //s.ishell.Request()
}
func (s *Shell) WriteStuff(data []byte, path string) {

  err := s.ishell.FilesWrite(context.Background(), path, bytes.NewReader(data),
    ipfsapi.FilesWrite.Create(true),
    ipfsapi.FilesWrite.Truncate(true),
    ipfsapi.FilesWrite.Parents(true))

  if err != nil {
    testing.TestLog.Error("Failed to write stuff: %s", err.Error())
  }
}

/**
Fileswrite

	fr := files.NewReaderFile(data)
	slf := files.NewSliceDirectory([]files.DirEntry{files.FileEntry("", fr)})
	fileReader := files.NewMultiFileReader(slf, true)


AddDir
	sf, err := files.NewSerialFile(dir, false, stat)
	if err != nil {
		return "", err
	}
	slf := files.NewSliceDirectory([]files.DirEntry{files.FileEntry(filepath.Base(dir), sf)})
	reader := files.NewMultiFileReader(slf, true)


DagPut
	fr := files.NewReaderFile(r)
	slf := files.NewSliceDirectory([]files.DirEntry{files.FileEntry("", fr)})
	fileReader := files.NewMultiFileReader(slf, true)

*/
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

func (req *RequestBuilder) Header(name, value string) {
  req.rb.Header(name, value)
}

func (req *RequestBuilder) BodyString(body string) {
  req.rb.BodyString(body)
}

func (req *RequestBuilder) BodyBytes(body []byte) {
  req.rb.BodyBytes(body)
}

func (req *RequestBuilder) PostData(data []byte, callback Callback) {
  fr := files.NewReaderFile(bytes.NewReader(data))
  slf := files.NewSliceDirectory([]files.DirEntry{files.FileEntry("", fr)})
  fileReader := files.NewMultiFileReader(slf, true)
  req.rb.Body(fileReader)
  res, err := req.rb.Send(context.Background())
  if err != nil {
    testing.TestLog.Error("Error: %s", err.Error())
    callback.OnError(err.Error())
  } else {
    respData, _ := ioutil.ReadAll(res.Output)
    testing.TestLog.Info("Response: %s", string(respData))
    callback.OnResponse(respData)
  }

  testing.TestLog.Debug("queued response")

}

func (req *RequestBuilder) PostString(data string) {
  req.post(strings.NewReader(data))
}

func (req *RequestBuilder) PostReader(data Reader) {
  req.post(data)
}

type ByteReader struct {
  Reader
  data []int8
  size int
  i    int
}

func NewTestReader(data []int8) Reader {
  var r = new(ByteReader)
  r.data = data
  r.size = len(data)
  r.i = -1
  return r
}

func (r *ByteReader) Read(p []byte) (int, error) {

  //println("REading ", r.i)
  if r.i == r.size {
    return 0, io.EOF
  }
  r.i = r.size
  for n := 0; n < r.size; n++ {
    p[n] = byte(r.data[n])
  }
  return r.size, nil
}

func (req *RequestBuilder) post(data io.Reader) {
  /*
     var r io.Reader
     switch data := data.(type) {
     case string:
       r = strings.NewReader(data)
     case []byte:
       println("data is byte[] of length", len(data))
       r = bytes.NewReader(data)
     case []int8:
       println("in8 array length: ", len(data))
       r = NewTestReader(data)

     case io.Reader:
       r = data
     }
  */
  fr := files.NewReaderFile(data)
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
