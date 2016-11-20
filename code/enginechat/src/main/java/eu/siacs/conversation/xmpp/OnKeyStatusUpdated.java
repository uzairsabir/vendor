package eu.siacs.conversation.xmpp;

import eu.siacs.conversation.crypto.axolotl.AxolotlService;

public interface OnKeyStatusUpdated {
	public void onKeyStatusUpdated(AxolotlService.FetchStatus report);
}
