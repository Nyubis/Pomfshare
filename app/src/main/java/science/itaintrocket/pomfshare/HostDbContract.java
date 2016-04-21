package science.itaintrocket.pomfshare;

import android.provider.BaseColumns;

public class HostDbContract {
	public static abstract class HostTable implements BaseColumns {
		public static final String TABLE_NAME = "hosts";
		public static final String COL_HOST_NAME = "name";
		public static final String COL_HOST_URL = "url";
		public static final String COL_HOST_DESC = "description";
		public static final String COL_HOST_API = "api";
	}

	public static final String SQL_CREATE_TABLE =
			"CREATE TABLE " + HostTable.TABLE_NAME + " (" +
					HostTable._ID + " INTEGER PRIMARY KEY," +
					HostTable.COL_HOST_NAME + " TEXT, " +
					HostTable.COL_HOST_URL + " TEXT, " +
					HostTable.COL_HOST_DESC + " TEXT, " +
					HostTable.COL_HOST_API + " TEXT " +
			" )";
}
