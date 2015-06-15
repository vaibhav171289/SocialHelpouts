package com.example.syncadapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import org.xmlpull.v1.XmlPullParserException;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.example.localdatabase.Jobs;

public class SyncAdapter extends AbstractThreadedSyncAdapter{
	public static final String TAG = "SyncAdapter";
	private static final String FEED_URL = "devtest.socialhelpouts.com/jobsJson/q?page=1";
	  /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds
    private static final String[] PROJECTION = new String[] {
        Jobs._ID,
        Jobs.COMPANY_NAME,
        Jobs.DESCRIPTION};
    
    public static final int COLUMN_ID = 0;
    public static final int COMPANY_NAME_ID = 1;
    public static final int DESCRIPTION_ID = 2;
    
	ContentResolver mContentResolver;
	private Context context;
	String serverurl="devtest.socialhelpouts.com/jobsJson/q?page=1";
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
this.context=context;
		mContentResolver=context.getContentResolver();
	}
	public SyncAdapter(Context context,boolean autoInitialize,boolean allowParallelSyncs)
	{
		super(context,autoInitialize,allowParallelSyncs);
		this.context=context;
		mContentResolver=context.getContentResolver();
	}
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		Log.i(TAG, "Beginning network synchronization");
        try {
            final URL location = new URL(FEED_URL);
            InputStream stream = null;

            try {
                Log.i(TAG, "Streaming data from network: " + location);
                stream = downloadUrl(location);
                updateLocalFeedData(stream, syncResult);
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (MalformedURLException e) {
            Log.wtf(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        }
        Log.i(TAG, "Network synchronization complete");
		
	}
	private InputStream downloadUrl(URL location) {
		// TODO Auto-generated method stub
		return null;
	}
	private void updateLocalFeedData(InputStream stream, SyncResult syncResult) {
		// TODO Auto-generated method stub
		
	}

}
