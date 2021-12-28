// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.files.File is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs github.com/ipfs/go-ipfs-files
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.files;

import go.Seq;

/**
 * Node represents a regular Unix file
 */
public interface File extends Node, go.kipfs.core.Closer, go.kipfs.core.ReadCloser, go.kipfs.core.Reader {
	public void close() throws Exception;
	public long read(byte[] p0) throws Exception;
	public long seek(long offset, long whence) throws Exception;
	public long size() throws Exception;
	
}

