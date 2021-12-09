// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.core.Core is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/core
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.core;

import go.Seq;

public abstract class Core {
	static {
		Seq.touch(); // for loading the native library
		_init();
	}
	
	private Core() {} // uninstantiable
	
	// touch is called from other bound packages to initialize this package
	public static void touch() {}
	
	private static native void _init();
	
	private static final class proxyJReader implements Seq.Proxy, JReader {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyJReader(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		public native long read(byte[] p0) throws Exception;
	}
	private static final class proxyReader implements Seq.Proxy, Reader {
		private final int refnum;
		
		@Override public final int incRefnum() {
		      Seq.incGoRef(refnum, this);
		      return refnum;
		}
		
		proxyReader(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
		
		public native long read(byte[] p0) throws Exception;
	}
	
	public static final String UDSDir = "sock";
	
	public static native void initRepo(String path, Config cfg) throws Exception;
	public static native Config newConfig(byte[] raw_json) throws Exception;
	public static native Config newDefaultConfig() throws Exception;
	public static native Node newNode(Repo r, boolean online) throws Exception;
	public static native Shell newShell(String url);
	public static native SockManager newSockManager(String path) throws Exception;
	public static native Shell newTCPShell(String port);
	/**
	 * New unix socket domain shell
	 */
	public static native Shell newUDSShell(String sockpath);
	public static native Repo openRepo(String path) throws Exception;
	public static native boolean repoIsInitialized(String path);
}
