package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.xmpp.stanzas.IqPacket;

public interface OnIqPacketReceived extends PacketReceived {
	public void onIqPacketReceived(Account account, IqPacket packet);
}
