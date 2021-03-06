// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.core.Repo is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/core
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.core;

import go.Seq;

public final class Repo implements Seq.Proxy, Closer {
	static { Core.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	Repo(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public Repo() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public native void close() throws Exception;
	public native Config getConfig() throws Exception;
	public native String getRootPath();
	public native void setConfig(Config c) throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof Repo)) {
		    return false;
		}
		Repo that = (Repo)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Repo").append("{");
		return b.append("}").toString();
	}
}

