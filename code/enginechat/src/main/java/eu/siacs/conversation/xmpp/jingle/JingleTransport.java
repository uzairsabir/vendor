package eu.siacs.conversation.xmpp.jingle;

import eu.siacs.conversation.entities.DownloadableFile;

public abstract class JingleTransport {
	public abstract void connect(final OnTransportConnected callback);

	public abstract void receive(final DownloadableFile file,
			final OnFileTransmissionStatusChanged callback);

	public abstract void send(final DownloadableFile file,
			final OnFileTransmissionStatusChanged callback);

	public abstract void disconnect();
}
