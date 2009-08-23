package de.hdmstuttgart.yaxim.chat;

import java.util.List;

import android.os.RemoteException;
import android.util.Log;
import de.hdmstuttgart.yaxim.service.IXMPPChatService;

public class XMPPChatServiceAdapter {

	private static final String TAG = "XMPPChatServiceAdapter";
	private IXMPPChatService xmppServiceStub;
	private String jabberID;

	public XMPPChatServiceAdapter(IXMPPChatService xmppServiceStub,
			String jabberID) {
		Log.i(TAG, "New XMPPChatServiceAdapter construced");
		this.xmppServiceStub = xmppServiceStub;
		this.jabberID = jabberID;
	}

	public void registerUICallback(IXMPPChatCallback uiCallback) {
		try {
			xmppServiceStub.registerChatCallback(uiCallback, jabberID);
		} catch (RemoteException e) {
			Log.e(TAG, "caught RemoteException: " + e.getMessage());
		}
	}

	public void unregisterUICallback(IXMPPChatCallback uiCallback) {
		try {
			xmppServiceStub.unregisterChatCallback(uiCallback, jabberID);
		} catch (RemoteException e) {
			Log.e(TAG, "caught RemoteException: " + e.getMessage());
		}
	}

	public void sendMessage(String user, String message) {
		try {
			Log.i(TAG, "Called sendMessage(): " + jabberID + ": " + message);
			xmppServiceStub.sendMessage(user, message);
		} catch (RemoteException e) {
			Log.e(TAG, "caught RemoteException: " + e.getMessage());
		}
	}

	public List<String> pullMessagesForContact(
			String jabberID) {
		List<String>  queue = null;
		try {
			queue = xmppServiceStub.pullMessagesForContact(jabberID);
		} catch (RemoteException e) {
			Log.e(TAG, "caught RemoteException: " + e.getMessage());
		}
		return queue;
	}
	
	public boolean isServiceAuthenticated() {
		try {
			return xmppServiceStub.isAuthenticated();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

}
