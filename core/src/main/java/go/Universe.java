// Code generated by gobind. DO NOT EDIT.

// Java class go.Universe is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs 
//go:build  linux || windows 
//+build linux, windows 
package go;

public abstract class Universe {
	static {
		Seq.touch(); // for loading the native library
		_init();
	}
	
	private Universe() {} // uninstantiable
	
	// touch is called from other bound packages to initialize this package
	public static void touch() {}
	
	private static native void _init();
	
	private static final class proxyerror extends Exception implements Seq.Proxy, error {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyerror(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		@Override public String getMessage() { return error(); }
		
		public native String error();
	}
	
	
}
