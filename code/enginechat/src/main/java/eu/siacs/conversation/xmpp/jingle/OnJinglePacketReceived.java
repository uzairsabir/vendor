package eu.siacs.conversation.xmpp.jingle;

import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.xmpp.PacketReceived;
import eu.siacs.conversation.xmpp.jingle.stanzas.JinglePacket;

public interface OnJinglePacketReceived extends PacketReceived {
	void onJinglePacketReceived(Account account, JinglePacket packet);
}
