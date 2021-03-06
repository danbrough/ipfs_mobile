// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.core.RequestBuilder is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/core
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.core;

import go.Seq;

public final class RequestBuilder implements Seq.Proxy {
	static { Core.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	RequestBuilder(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public RequestBuilder() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public native void argument(String arg);
	public native void bodyBytes(byte[] body);
	public native void bodyString(String body);
	public native void boolOptions(String key, boolean value);
	public native void byteOptions(String key, byte[] value);
	public native void header(String name, String value);
	public native byte[] post2(String name, go.kipfs.files.Node file) throws Exception;
	public native Response post4(KReader body) throws Exception;
	public native void postData(byte[] data, Callback callback);
	public native byte[] postData2(byte[] data) throws Exception;
	public native Response postData3(byte[] data) throws Exception;
	public native void postDirectory(String dir, Callback callback);
	public native void postReader(String name, KReader data, Callback callback);
	public native void postString(String data, Callback callback);
	public native byte[] postString2(String data) throws Exception;
	public native Response postString3(String data) throws Exception;
	public native byte[] send() throws Exception;
	// skipped method RequestBuilder.Send2 with unsupported parameter or return types
	
	public native void stringOptions(String key, String value);
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof RequestBuilder)) {
		    return false;
		}
		RequestBuilder that = (RequestBuilder)o;
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("RequestBuilder").append("{");
		return b.append("}").toString();
	}
}

