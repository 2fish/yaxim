package org.yaxim.androidclient.service;

import org.yaxim.androidclient.chat.ChatWindow;
import org.yaxim.androidclient.data.YaximConfiguration;
import org.yaxim.androidclient.util.LogConstants;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import org.yaxim.androidclient.R;

public abstract class GenericService extends Service {

	private static final String TAG = "Service";
	private static final String APP_NAME = "Yaxim";
	private static final int NOTIFY_ID = 0;

	private NotificationManager notificationMGR;
	private Notification notification;
	private Vibrator vibrator;
	private Intent notificationIntent;
	private int notificationCounter = 0;

	protected YaximConfiguration mConfig;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i(TAG, "called onBind()");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.i(TAG, "called onUnbind()");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		Log.i(TAG, "called onRebind()");
		super.onRebind(intent);
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "called onCreate()");
		super.onCreate();
		mConfig = new YaximConfiguration(PreferenceManager
				.getDefaultSharedPreferences(this));
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		addNotificationMGR();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "called onDestroy()");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "called onStart()");
		super.onStart(intent, startId);
	}

	private void addNotificationMGR() {
		notificationMGR = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationIntent = new Intent(this, ChatWindow.class);
	}

	protected void notifyClient(String from, String message) {
		notificationCounter++;
		String title = "Message from " + from;
		notification = new Notification(R.drawable.icon, APP_NAME + ": "
				+ title, System.currentTimeMillis());
		Uri userNameUri = Uri.parse(from);
		notificationIntent.setData(userNameUri);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		notification.setLatestEventInfo(this, title, message, pendingIntent);
		notification.number = notificationCounter;
		notification.flags = Notification.FLAG_AUTO_CANCEL
				| Notification.FLAG_ONLY_ALERT_ONCE;

		vibraNotififaction();

		if (mConfig.isLEDNotify) {
			notification.flags |= Notification.DEFAULT_LIGHTS;
			notification.ledARGB = Color.MAGENTA;
			notification.ledOnMS = 300;
			notification.ledOffMS = 1000;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		}

		notificationMGR.notify(NOTIFY_ID, notification);
	}

	private void vibraNotififaction() {
		if (mConfig.isVibraNotify) {
			vibrator.vibrate(500);
		}
	}

	protected void shortToastNotify(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void resetNotificationCounter() {
		notificationCounter = 0;
	}
	
	protected void logError(String data) {
		if (LogConstants.LOG_ERROR) {
			Log.e(TAG, data);
		}
	}
	
	protected void logInfo(String data) {
		if (LogConstants.LOG_ERROR) {
			Log.e(TAG, data);
		}
	}

}
