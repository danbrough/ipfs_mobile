// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.files.DirEntry is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs github.com/ipfs/go-ipfs-files
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.files;

import go.Seq;

/**
 * DirEntry exposes information about a directory entry
 */
public interface DirEntry {
	/**
	 * Name returns base name of this entry, which is the base name of referenced
	file
	 */
	public String name();
	/**
	 * Node returns the file referenced by this DirEntry
	 */
	public Node node();
	
}

