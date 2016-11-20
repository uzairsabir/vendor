package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Contact;

public interface OnContactStatusChanged {
	public void onContactStatusChanged(final Contact contact, final boolean online);
}
