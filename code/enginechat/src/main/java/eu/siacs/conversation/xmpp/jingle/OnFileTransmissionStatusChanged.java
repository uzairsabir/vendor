package eu.siacs.conversation.xmpp.jingle;

import eu.siacs.conversation.entities.DownloadableFile;

public interface OnFileTransmissionStatusChanged {
	void onFileTransmitted(DownloadableFile file);

	void onFileTransferAborted();
}
