// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.files.WebFile is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs github.com/ipfs/go-ipfs-files
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.files;

import go.Seq;

/**
 * WebFile is an implementation of File which reads it
from a Web URL (http). A GET request will be performed
against the source when calling Read().
 */
public final class WebFile implements Seq.Proxy, File, FileInfo, Node, go.kipfs.core.JReader, go.kipfs.core.Reader {
	static { Files.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	// skipped constructor WebFile.NewWebFile with unsupported parameter or return types
	
	WebFile(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public native String absPath();
	/**
	 * Close closes the WebFile (or the request body).
	 */
	public native void close() throws Exception;
	/**
	 * Read reads the File from it&#39;s web location. On the first
	call to Read, a GET request will be performed against the
	WebFile&#39;s URL, using Go&#39;s default HTTP client. Any further
	reads will keep reading from the HTTP Request body.
	 */
	public native long read(byte[] b) throws Exception;
	/**
	 * TODO: implement
	 */
	public native long seek(long offset, long whence) throws Exception;
	public native long size() throws Exception;
	// skipped method WebFile.Stat with unsupported parameter or return types
	
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof WebFile)) {
		    return false;
		}
		WebFile that = (WebFile)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("WebFile").append("{");
		return b.append("}").toString();
	}
}
