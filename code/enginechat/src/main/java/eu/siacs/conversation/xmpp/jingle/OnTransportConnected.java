package eu.siacs.conversation.xmpp.jingle;

public interface OnTransportConnected {
	public void failed();

	public void established();
}
