// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.files.Symlink is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs github.com/ipfs/go-ipfs-files
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.files;

import go.Seq;

public final class Symlink implements Seq.Proxy, File, Node, go.kipfs.core.Reader {
	static { Files.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	Symlink(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public Symlink() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public final native String getTarget();
	public final native void setTarget(String v);
	
	public native void close() throws Exception;
	public native long read(byte[] b) throws Exception;
	public native long seek(long offset, long whence) throws Exception;
	public native long size() throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof Symlink)) {
		    return false;
		}
		Symlink that = (Symlink)o;
		String thisTarget = getTarget();
		String thatTarget = that.getTarget();
		if (thisTarget == null) {
			if (thatTarget != null) {
			    return false;
			}
		} else if (!thisTarget.equals(thatTarget)) {
		    return false;
		}
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {getTarget()});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Symlink").append("{");
		b.append("Target:").append(getTarget()).append(",");
		return b.append("}").toString();
	}
}

