package eu.siacs.conversation.entities;

import eu.siacs.conversation.xmpp.jid.Jid;

public interface Blockable {
	public boolean isBlocked();
	public boolean isDomainBlocked();
	public Jid getBlockedJid();
	public Jid getJid();
	public Account getAccount();
}
