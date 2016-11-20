package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;

public interface OnMessageAcknowledged {
	public void onMessageAcknowledged(Account account, String id);
}
