// Code generated by gobind. DO NOT EDIT.

// Java class kipfs.core.Config is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=kipfs kipfs/core
package kipfs.core;

import go.Seq;

public final class Config implements Seq.Proxy {
	static { Core.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	public Config(byte[] raw_json) {
		this.refnum = __NewConfig(raw_json);
		Seq.trackGoRef(refnum, this);
	}
	
	private static native int __NewConfig(byte[] raw_json);
	
	Config(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public native byte[] get() throws Exception;
	public native byte[] getKey(String key) throws Exception;
	public native void set(byte[] raw_json) throws Exception;
	public native void setKey(String key, byte[] raw_value) throws Exception;
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof Config)) {
		    return false;
		}
		Config that = (Config)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Config").append("{");
		return b.append("}").toString();
	}
}

