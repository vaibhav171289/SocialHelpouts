package com.example.localdatabase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "jobdatabasehelper";
	
	 
	    public static final String JOB_TABLE_NAME = "jobdata";
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub	
		db.execSQL("CREATE TABLE "+JOB_TABLE_NAME+"("+Jobs._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+Jobs.COMPANY_NAME+" VARCHAR(255),"+Jobs.DESCRIPTION+" LONGTEXT" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.i(TAG,"Upgrading database from version ");
		db.execSQL("DROP TABLE IF EXISTS"+ JOB_TABLE_NAME);
		onCreate(db);
	}

}
