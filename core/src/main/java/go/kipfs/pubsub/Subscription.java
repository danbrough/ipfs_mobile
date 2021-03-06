// Code generated by gobind. DO NOT EDIT.

// Java class go.kipfs.pubsub.Subscription is a proxy for talking to a Go program.
//
//   autogenerated by gobind -lang=java -javapkg=go.kipfs kipfs/pubsub
//go:build  linux || windows 
//+build linux, windows 
package go.kipfs.pubsub;

import go.Seq;

public final class Subscription implements Seq.Proxy {
	static { Pubsub.touch(); }
	
	private final int refnum;
	
	@Override public final int incRefnum() {
	      Seq.incGoRef(refnum, this);
	      return refnum;
	}
	
	Subscription(int refnum) { this.refnum = refnum; Seq.trackGoRef(refnum, this); }
	
	public Subscription() { this.refnum = __New(); Seq.trackGoRef(refnum, this); }
	
	private static native int __New();
	
	public final native String getTopic();
	public final native void setTopic(String v);
	
	public final native SubscriptionListener getListener();
	public final native void setListener(SubscriptionListener v);
	
	@Override public boolean equals(Object o) {
		if (o == null || !(o instanceof Subscription)) {
		    return false;
		}
		Subscription that = (Subscription)o;
		String thisTopic = getTopic();
		String thatTopic = that.getTopic();
		if (thisTopic == null) {
			if (thatTopic != null) {
			    return false;
			}
		} else if (!thisTopic.equals(thatTopic)) {
		    return false;
		}
		SubscriptionListener thisListener = getListener();
		SubscriptionListener thatListener = that.getListener();
		if (thisListener == null) {
			if (thatListener != null) {
			    return false;
			}
		} else if (!thisListener.equals(thatListener)) {
		    return false;
		}
		return true;
	}
	
	@Override public int hashCode() {
	    return java.util.Arrays.hashCode(new Object[] {getTopic(), getListener()});
	}
	
	@Override public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Subscription").append("{");
		b.append("Topic:").append(getTopic()).append(",");
		b.append("Listener:").append(getListener()).append(",");
		return b.append("}").toString();
	}
}

