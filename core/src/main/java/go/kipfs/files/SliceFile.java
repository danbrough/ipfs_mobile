// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.files.SliceFile is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs github.com/ipfs/go-ipfs-files
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.files;

import go.Seq;

/**
 * SliceFile implements Node, and provides simple directory handling.
It contains children files, and is created from a `[]Node`.
SliceFiles are always directories, and can&#39;t be read from or closed.
 */
public final class SliceFile implements Seq.Proxy, Directory, Node, go.kipfs.core.Closer {
	static { Files.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	SliceFile(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public SliceFile() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public native void close() throws Exception;
	public native DirIterator entries();
	public native long length();
	public native long size() throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof SliceFile)) {
		    return false;
		}
		SliceFile that = (SliceFile)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("SliceFile").append("{");
		return b.append("}").toString();
	}
}

