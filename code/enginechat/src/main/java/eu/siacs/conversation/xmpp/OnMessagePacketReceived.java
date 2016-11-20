package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.xmpp.stanzas.MessagePacket;

public interface OnMessagePacketReceived extends PacketReceived {
	public void onMessagePacketReceived(Account account, MessagePacket packet);
}
