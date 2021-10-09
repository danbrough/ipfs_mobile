package main

/*
#include <stdlib.h>
*/
import "C"
import "unsafe"
import "fmt"

func main() {
    s := "Hello World!"
    cs := C.CString(s)
    C.free(unsafe.Pointer(cs))
    fmt.Println(s)
}

