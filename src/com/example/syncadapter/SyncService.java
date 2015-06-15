package com.example.syncadapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service{
	private static final String TAG = "SyncService";
	private static SyncAdapter mSyncAdapter=null;
			private static final Object mSyncAdapterLock=  new Object();
			
	@Override
	public void onCreate()
	{
		synchronized(mSyncAdapterLock)
		{
			if(mSyncAdapter==null)
			{
				mSyncAdapter= new SyncAdapter(getApplicationContext(),true);
			}
		}
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		 Log.i(TAG, "Service destroyed");
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mSyncAdapter.getSyncAdapterBinder();
	}

}
