package eu.siacs.conversation.services;

import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import eu.siacs.conversation.Config;
import eu.siacs.conversation.R;
import eu.siacs.conversation.entities.Account;
import eu.siacs.conversation.xml.Element;
import eu.siacs.conversation.xmpp.OnIqPacketReceived;
import eu.siacs.conversation.xmpp.XmppConnection;
import eu.siacs.conversation.xmpp.forms.Data;
import eu.siacs.conversation.xmpp.jid.InvalidJidException;
import eu.siacs.conversation.xmpp.jid.Jid;
import eu.siacs.conversation.xmpp.stanzas.IqPacket;

public class PushManagementService {

	private static final String APP_SERVER = "push.siacs.eu";

	protected final XmppConnectionService mXmppConnectionService;

	public PushManagementService(XmppConnectionService service) {
		this.mXmppConnectionService = service;
	}

	public void registerPushTokenOnServer(final Account account) {
		Log.d(Config.LOGTAG, account.getJid().toBareJid() + ": has push support");
		retrieveGcmInstanceToken(new OnGcmInstanceTokenRetrieved() {
			@Override
			public void onGcmInstanceTokenRetrieved(String token) {
				try {
					final String deviceId = Settings.Secure.getString(mXmppConnectionService.getContentResolver(), Settings.Secure.ANDROID_ID);
					IqPacket packet = mXmppConnectionService.getIqGenerator().pushTokenToAppServer(Jid.fromString(APP_SERVER), token, deviceId);
					mXmppConnectionService.sendIqPacket(account, packet, new OnIqPacketReceived() {
						@Override
						public void onIqPacketReceived(Account account, IqPacket packet) {
							Element command = packet.findChild("command","http://jabber.org/protocol/commands");
							if (packet.getType() == IqPacket.TYPE.RESULT && command != null) {
								Element x = command.findChild("x","jabber:x:data");
								if (x != null) {
									Data data = Data.parse(x);
									try {
										String node = data.getValue("node");
										String secret = data.getValue("secret");
										Jid jid = Jid.fromString(data.getValue("jid"));
										if (node != null && secret != null) {
											enablePushOnServer(account, jid, node, secret);
										}
									} catch (InvalidJidException e) {
										e.printStackTrace();
									}
								}
							} else {
								Log.d(Config.LOGTAG,account.getJid().toBareJid()+": invalid response from app server");
							}
						}
					});
				} catch (InvalidJidException ignored) {

				}
			}
		});
	}

	private void enablePushOnServer(final Account account, final Jid jid, final String node, final String secret) {
		IqPacket enable = mXmppConnectionService.getIqGenerator().enablePush(jid, node, secret);
		mXmppConnectionService.sendIqPacket(account, enable, new OnIqPacketReceived() {
			@Override
			public void onIqPacketReceived(Account account, IqPacket packet) {
				if (packet.getType() == IqPacket.TYPE.RESULT) {
					Log.d(Config.LOGTAG, account.getJid().toBareJid() + ": successfully enabled push on server");
				} else if (packet.getType() == IqPacket.TYPE.ERROR) {
					Log.d(Config.LOGTAG, account.getJid().toBareJid() + ": enabling push on server failed");
				}
			}
		});
	}

	private void retrieveGcmInstanceToken(final OnGcmInstanceTokenRetrieved instanceTokenRetrieved) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				InstanceID instanceID = InstanceID.getInstance(mXmppConnectionService);
				try {
					String token = instanceID.getToken(mXmppConnectionService.getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
					instanceTokenRetrieved.onGcmInstanceTokenRetrieved(token);
				} catch (Exception e) {
					Log.d(Config.LOGTAG,"unable to get push token");
				}
			}
		}).start();

	}


	public boolean available(Account account) {
		final XmppConnection connection = account.getXmppConnection();
		return connection != null && connection.getFeatures().push() && playServicesAvailable();
	}

	private boolean playServicesAvailable() {
		return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mXmppConnectionService) == ConnectionResult.SUCCESS;
	}

	public boolean isStub() {
		return false;
	}

	interface OnGcmInstanceTokenRetrieved {
		void onGcmInstanceTokenRetrieved(String token);
	}
}
