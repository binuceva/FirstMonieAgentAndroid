package sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	// Database attributes
	public static final String DB_NAME = "bio_app";

	public static final int DB_VERSION = 1;
	// Table attributes
	public static final String MEMBERS_TABLE = "members";
	// various table fields
	public static final String ID = "id";
	public static final String SERIAL_NO = "serial_no";
	public static final String NATIONAL_ID = "national_id";
	public static final String FULL_NAMES = "full_names";
	public static final String MOBILE_NUMBER = "mobile_number";
	public static final String MEMBERSHIP_NUMBER = "membership_number";
	public static final String BIO_HEX = "bio_hex";
	public static final String RAW_DATA = "raw_data";
	public static final String HAND = "hand";
	public static final String FINGER = "finger";
	private SQLiteDatabase sqliteDBInstance = null;

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	private static final String CREATE_TABLE_MEDS = " create table if not exists "
			+ MEMBERS_TABLE
			+ " "
			+ "( "
			+ ID
			+ " integer primary key autoincrement, "
			+ SERIAL_NO
			+ " text not null, "
			+ NATIONAL_ID
			+ " text not null, "
			+ FULL_NAMES
			+ " text not null, "
			+ MOBILE_NUMBER
			+ " text not null, "
			+ MEMBERSHIP_NUMBER
			+ " text not null, "
			+ HAND
			+ " text not null, "
			+ FINGER
			+ " text not null, "
			+ RAW_DATA
			+ " text not null, "
			+  BIO_HEX + " text not null);";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MEDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void openDB() throws SQLException {
		if (this.sqliteDBInstance == null) {
			this.sqliteDBInstance = this.getWritableDatabase();
		}
	}

	public void closeDB() {
		if (this.sqliteDBInstance != null) {
			if (this.sqliteDBInstance.isOpen())
				this.sqliteDBInstance.close();
		}
	}
}
