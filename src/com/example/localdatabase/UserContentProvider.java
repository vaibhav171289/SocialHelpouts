package com.example.localdatabase;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class UserContentProvider extends ContentProvider {
	private static final String TAG = "NotesContentProvider";
	 
	 private static final String DATABASE_NAME = "Jobs.db";
	 
	    private static final int DATABASE_VERSION = 1;
 
    public static final String AUTHORITY = "com.example.localdatabase.UserContentProvider";
    private static final int Company_name = 2;

    private static final int jobdata = 1;
    private static final int JOB_ID = 3;
    private static final int description = 4;
    private static final UriMatcher URI_MATCHER;
    private static HashMap<String, String> jobProjectionMap;
    static {
    	URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    	URI_MATCHER.addURI(UserContentProvider.AUTHORITY,
    	      "jobdata",
    	      jobdata);
    	URI_MATCHER.addURI(UserContentProvider.AUTHORITY,
    	      "jobdata/#",
    	      JOB_ID);
    	URI_MATCHER.addURI(UserContentProvider.AUTHORITY,
    	      "jobdata/#",
    	      Company_name);
    	jobProjectionMap= new HashMap<>();
    	jobProjectionMap.put(Jobs.JOB_ID,Jobs.JOB_ID);
    	jobProjectionMap.put(Jobs.COMPANY_NAME,Jobs.COMPANY_NAME);
    	jobProjectionMap.put(Jobs.DESCRIPTION, Jobs.DESCRIPTION);
    }
    
 
   
 
    private DatabaseHelper dbHelper;
	@Override
	public boolean onCreate() {
 DatabaseErrorHandler errorHandler = new DatabaseErrorHandler() {
	
	@Override
	public void onCorruption(SQLiteDatabase dbObj) {
		Log.i(TAG,"error in on create Usercontentprovider");
	}
};
dbHelper= new DatabaseHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION, errorHandler);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder= new SQLiteQueryBuilder();
		builder.setTables(DatabaseHelper.JOB_TABLE_NAME);
		builder.setProjectionMap(jobProjectionMap);
		int uritype= URI_MATCHER.match(uri);
		switch(uritype)
		{
		case jobdata: break;
		case JOB_ID: selection = selection+"_id="+uri.getLastPathSegment();
		break;
		default: throw new IllegalArgumentException("unknown uri "+uri);
		}
		SQLiteDatabase db= dbHelper.getReadableDatabase();
		Cursor c= builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		c.close();
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch(URI_MATCHER.match(uri))
		{
		case jobdata: return Jobs.CONTENT_TYPE;
		default:
            throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialvalues) {
		if(URI_MATCHER.match(uri)!=jobdata)
		{
			throw new IllegalArgumentException("Unknown uri "+uri);
		}
		ContentValues values;
		if(initialvalues!=null)
		{
			values=new ContentValues(initialvalues);
		}
		else
		{
			values= new ContentValues();
		}
		SQLiteDatabase db= dbHelper.getWritableDatabase();
		long rowId=db.insert(DatabaseHelper.JOB_TABLE_NAME, Jobs.DESCRIPTION, values);
		if(rowId>0)
		{
			Uri joburi=ContentUris.withAppendedId(Jobs.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(joburi,null);
			return joburi;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
