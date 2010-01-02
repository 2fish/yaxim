package org.yaxim.androidclient.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

	public static final String ACCOUNTS = "accounts";
	public static final String ROSTER = "roster";
	public static final String CHATS = "chats";

	private static final String TAG = "DataBaseHelper";
	private static final String DATABASE_NAME = "yaxim.db";
	private static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.d(TAG, "created new database");

		db.execSQL("CREATE TABLE " + ACCOUNTS + " ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT," + "user_name TEXT,"
				+ "server TEXT," + "port INTEGER," + "password TEXT,"
				+ "ressource TEXT," + "default_priority INTEGER,"
				+ "away_priority INTEGER," + "status_message TEXT,"
				+ "auto_reconnect BOOLEAN" + "auto_connect BOOLEAN" + ");");

		db.execSQL("CREATE TABLE " + ROSTER + " ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "account_id INTEGER," + "jabberid TEXT,"
				+ "screen_name TEXT," + "status_mode INTEGER"
				+ "status_message TEXT," + "group TEXT" + ");");

		db.execSQL("CREATE TABLE " + CHATS + " ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT," + "time TIMESTAMP,"
				+ "fromJID TEXT," + "toJID TEXT," + "message TEXT," + "read BOOLEAN,"
				+ "from_user BOOLEAN" + ");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ". All data will be deleted!");

		db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS);
		db.execSQL("DROP TABLE IF EXISTS " + ROSTER);
		db.execSQL("DROP TABLE IF EXISTS " + CHATS);

		onCreate(db);
	}

}
