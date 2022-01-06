// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.core.Response is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/core
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.core;

import go.Seq;

public final class Response implements Seq.Proxy, Closer, KReader, ReadCloser, Reader {
	static { Core.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	Response(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public Response() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public native void close() throws Exception;
	public native long read(byte[] p0) throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof Response)) {
		    return false;
		}
		Response that = (Response)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Response").append("{");
		return b.append("}").toString();
	}
}

