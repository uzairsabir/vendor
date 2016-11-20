package eu.siacs.conversation.xmpp.stanzas.csi;

import eu.siacs.conversation.xmpp.stanzas.AbstractStanza;

public class ActivePacket extends AbstractStanza {
	public ActivePacket() {
		super("active");
		setAttribute("xmlns", "urn:xmpp:csi:0");
	}
}
