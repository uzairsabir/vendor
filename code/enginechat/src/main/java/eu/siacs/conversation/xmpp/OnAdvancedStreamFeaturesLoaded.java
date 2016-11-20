package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.entities.Account;

public interface OnAdvancedStreamFeaturesLoaded {
	public void onAdvancedStreamFeaturesAvailable(final Account account);
}
