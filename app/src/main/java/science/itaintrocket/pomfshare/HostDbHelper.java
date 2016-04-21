package science.itaintrocket.pomfshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HostDbHelper extends SQLiteOpenHelper {
	// Version is to be changed whenever the schema changes
	// Then android knows to call onUpgrade for transitioning
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Hosts.db";


	public HostDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		// Create table
		db.execSQL(HostDbContract.SQL_CREATE_TABLE);
		// Insert default values
		for(ContentValues v: getDefaultHosts()) {
			db.insert(HostDbContract.HostTable.TABLE_NAME, null, v);
		}
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Upgrade code comes here when I actually do a schema change
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Downgrade code comes here when I actually do a schema change
	}

	private ContentValues[] getDefaultHosts() {
		// TODO: Clean this mess up — also remember that there's more of these in HostListActivity
		Host[] hosts = new Host[5];
		hosts[0] = new Host("Uguu", "https://uguu.se/api.php?d=upload-tool", "100MiB, 24 hours", Host.Type.UGUU);
		hosts[1] = new Host("Mixtape.moe", "https://mixtape.moe/upload.php?output=gyazo", "100MiB", Host.Type.POMF);
		hosts[2] = new Host("pomf.io", "https://pomf.io/upload.php?output=gyazo", "256MiB", Host.Type.POMF);
		hosts[3] = new Host("Maxfile", "https://maxfile.ro/static/upload.php?output=gyazo", "50MiB", Host.Type.POMF);
		hosts[4] = new Host("SICP", "http://sicp.me/", "25MiB", Host.Type.UGUU);

		ContentValues[] values = new ContentValues[5];
		for (int i=0; i<hosts.length; i++) {
			values[i] = new ContentValues();
			values[i].put(HostDbContract.HostTable.COL_HOST_NAME, hosts[i].getName());
			values[i].put(HostDbContract.HostTable.COL_HOST_URL, hosts[i].getUrl());
			values[i].put(HostDbContract.HostTable.COL_HOST_DESC, hosts[i].getDescription());
			values[i].put(HostDbContract.HostTable.COL_HOST_API, hosts[i].getType().name());
		}
		return values;
	}
}
