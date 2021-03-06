// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.core.SockManager is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/core
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.core;

import go.Seq;

public final class SockManager implements Seq.Proxy {
	static { Core.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	public SockManager(String path) {
		this.refnum = __NewSockManager(path);
		Seq.trackGoRef(refnum, this);
	}
	
	private static native int __NewSockManager(String path);
	
	SockManager(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public native String newSockPath() throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof SockManager)) {
		    return false;
		}
		SockManager that = (SockManager)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("SockManager").append("{");
		return b.append("}").toString();
	}
}

