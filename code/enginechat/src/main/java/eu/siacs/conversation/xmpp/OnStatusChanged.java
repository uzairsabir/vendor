package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;

public interface OnStatusChanged {
	public void onStatusChanged(Account account);
}
