package main

import (
  "bytes"
  "fmt"
  "github.com/ipfs/go-cid"
  "github.com/ipld/go-ipld-prime/codec/dagcbor"
  "github.com/ipld/go-ipld-prime/codec/dagjson"
  "github.com/ipld/go-ipld-prime/node/bindnode"
  "github.com/ipld/go-ipld-prime/schema"
  mh "github.com/multiformats/go-multihash"
  "os"
)

func test1() {
  ts := schema.TypeSystem{}
  ts.Init()
  ts.Accumulate(schema.SpawnString("String"))
  ts.Accumulate(schema.SpawnInt("Int"))
  ts.Accumulate(schema.SpawnStruct("Person",
    []schema.StructField{
      schema.SpawnStructField("Name", "String", false, false),
      schema.SpawnStructField("Age", "Int", true, false),
      schema.SpawnStructField("Friends", "List_String", false, false),
    },
    schema.SpawnStructRepresentationMap(nil),
  ))
  ts.Accumulate(schema.SpawnList("List_String", "String", false))

  schemaType := ts.TypeByName("Person")

  type Person struct {
    Name    string
    Age     *int64 // optional
    Friends []string
  }
  person := &Person{
    Name:    "Michael",
    Friends: []string{"Sarah", "Alex"},
  }
  node := bindnode.Wrap(person, schemaType)

  nodeRepr := node.Representation()
  dagjson.Encode(nodeRepr, os.Stdout)

  /*  pref := cid.Prefix{
      Version: 1,
      Codec: cid.Raw,
      MhType: mh.SHA2_256,
      MhLength: -1, // default length
    }*/

  var cidPrefix = cid.Prefix{
    Codec:    cid.DagCBOR,
    MhLength: -1,
    MhType:   mh.SHA2_256,
    Version:  1,
  }

  // And then feed it some data

  var buf bytes.Buffer
  //dagcbor.Encode(nodeRepr, &buf)
  f, _ := os.Create("/tmp/data.cbor")
  dagcbor.Encode(nodeRepr, f)
  f.Close()
  dagcbor.Encode(nodeRepr, &buf)

  c, err := cidPrefix.Sum(buf.Bytes())
  if err != nil {
    panic(err)
  }

  fmt.Println("Created CID: ", c)

  // Output:
  // {"Friends":["Sarah","Alex"],"Name":"Michael"}
}

func main() {
  test1()
}
