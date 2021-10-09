package main

import (
  "bytes"
  "fmt"
)

func test(format string, a ...interface{}) string {
  var buf bytes.Buffer
  _, _ = fmt.Fprintf(&buf, format, a...)
  return buf.String()
}
func main() {

  //println("DAG TEST", cids.DagCid(`"Hello World"`))

  println(test("%s age is %d height: %.2f", "Hello", 123, 123.4567))

}
