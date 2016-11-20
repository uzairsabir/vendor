package eu.siacs.conversation.xmpp.stanzas;

public class PresencePacket extends AbstractAcknowledgeableStanza {

	public PresencePacket() {
		super("presence");
	}
}
