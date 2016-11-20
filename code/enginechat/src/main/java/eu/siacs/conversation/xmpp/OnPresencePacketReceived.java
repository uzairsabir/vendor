package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.xmpp.stanzas.PresencePacket;

public interface OnPresencePacketReceived extends PacketReceived {
	public void onPresencePacketReceived(Account account, PresencePacket packet);
}
