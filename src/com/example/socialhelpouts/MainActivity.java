package com.example.socialhelpouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.util.MainPagerAdapter;
import com.example.util.MyJSONParser;

public class MainActivity extends Activity {
	private ProgressDialog dialog;
	MyJSONParser jParser;
	//ArrayList<HashMap<String, ArrayList<String>>> lst;
	ArrayList<HashMap<String,String>> lst;
	private static String url_all_jobs ="http://devtest.socialhelpouts.com/jobsJson/q?page=1";
	private static final String TAG_JOBS = "jobs";
	private static final String TAG_ID = "id";
	private static final String TAG_COMPANY = "company";
	private static final String TAG_NAME = "name";
	private static final String TAG_COMMENT = "comment";
	static LoadAllJobs aTask;
	JSONArray jobsarray=null;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	MainPagerAdapter pagerAdapter= null;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	public ViewPager mViewPager;
	private static int NUM_PAGES = 5;
	int count=0;
	String serverurl="http://devtest.socialhelpouts.com/jobsJson/q?page=1";
	TextView job_id,company_name,name,comment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		job_id=(TextView)findViewById(R.id.job_id);
		company_name=(TextView)findViewById(R.id.company_name);
		name=(TextView)findViewById(R.id.name);
		comment=(TextView)findViewById(R.id.comment);
//		jParser= new MyJSONParser();
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(), NUM_PAGES);
//		pagerAdapter= new MainPagerAdapter();
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
//		mViewPager.setAdapter(pagerAdapter);
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                invalidateOptionsMenu();
            	count++;
//                new LoadAllJobs(job_id, company_name, name, comment).execute();
            }
        });
//	        LayoutInflater inflater = ((Activity) getApplicationContext()).getLayoutInflater();
//	        FrameLayout v0 = (FrameLayout) inflater.inflate (R.layout.fragment_main, null);
//	        pagerAdapter.addView (v0, 0);
//       new LoadAllJobs().execute();
//	        aTask= new LoadAllJobs();
	}
	public void addView (View newPage)
	  {
	    int pageIndex = pagerAdapter.addView (newPage);
	    // You might want to make "newPage" the currently displayed page:
	    mViewPager.setCurrentItem (pageIndex, true);
	  }
	public void removeView (View defunctPage)
	  {
	    int pageIndex = pagerAdapter.removeView (mViewPager, defunctPage);
	    // You might want to choose what page to display, if the current page was "defunctPage".
	    if (pageIndex == pagerAdapter.getCount())
	      pageIndex--;
	    mViewPager.setCurrentItem (pageIndex);
	  }
	public View getCurrentPage ()
	  {
	    return pagerAdapter.getView (mViewPager.getCurrentItem());
	  }
	public void setCurrentPage (View pageToShow)
	  {
		mViewPager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		int NUM_PAGES=0;
		public SectionsPagerAdapter(FragmentManager fm,int NUM_PAGES) {
			super(fm);
			this.NUM_PAGES=NUM_PAGES;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			count=position;
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		private int mPageNumber;
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mPageNumber = getArguments().getInt(ARG_SECTION_NUMBER);
	    }
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			ViewGroup rootView =(ViewGroup) inflater.inflate(R.layout.fragment_main, container,
					false);
			
			//((TextView)rootView.findViewById(R.id.text1)).setText(getString(R.string.data,mPageNumber+1));
			return rootView;
		}
		
		public int getPageNumber() {
	        return mPageNumber;
	    }
		
		/*@Override
		public void setUserVisibleHint(boolean isVisibleToUser) {
		    super.setUserVisibleHint(isVisibleToUser);

		    if (isVisibleToUser) {

		        // launch your AsyncTask here, if the task has not been executed yet
		        if(aTask.getStatus().equals(AsyncTask.Status.PENDING)) {
		            aTask.execute();
		        }
		    }
	}*/
	}
	 class LoadAllJobs extends AsyncTask<String, String, String>
	 {
	 	TextView job_id,company_name,name,comment;
	 	HashMap<String, String> map;
	 	public LoadAllJobs(TextView job_id,TextView company_name,TextView name,TextView comment)
	 	{
	 		super();
	 		this.job_id=job_id;
	 		this.company_name=company_name;
	 		this.name=name;
	 		this.comment=comment;
	 	}
	 	@Override
	 	protected void onPreExecute()
	 	{
	 		super.onPreExecute();
	 		/*dialog=new ProgressDialog(context)
	 		dialog.setMessage("Loading Jobs. Please wait...");
	 		dialog.setIndeterminate(false);
	 		dialog.setCancelable(false);
	 		dialog.show();*/
	 	}
	 	@SuppressWarnings("deprecation")
	 	@Override
	 	protected String doInBackground(String... params) {
	 		List<NameValuePair> args = new ArrayList<NameValuePair>();
	         // getting JSON string from URL
	 	    Log.i("All Jobs: ", "check 0");
	         JSONObject json = jParser.makeHttpRequest(url_all_jobs, "GET", args);

	         // Check your log cat for JSON reponse
	         Log.i("All Jobs: ", "check 1");

	         try {
	             // Checking for SUCCESS TAG
	                 // Jobs found
	                 // Getting Array of Jobs
	                 jobsarray = json.getJSONArray(TAG_JOBS);
	                 Log.i("All Jobs: ", "check 2--->" +jobsarray.length());
	                 MainPagerAdapter.NUM_PAGES=jobsarray.length();
	                 // looping through All Jobs
//	                 for (int i = 0; i < jobsarray.length(); i++) 
	                 {
	                     JSONObject c = jobsarray.getJSONObject(count);

	                     // Storing each json item in variable
	                     String id = c.getString(TAG_ID);
	                     String name = c.getString(TAG_NAME);
	                     String company=c.getString(TAG_COMPANY);
	                     String comment=c.getString(TAG_COMMENT);
	                     Log.i("All Jobs: ", "check 3");
	                    // jobslist.add(id);
//	                     jobslist.add(company);
//	                     jobslist.add(name);
//	                     jobslist.add(comment);
	                     // creating new HashMap
//	                     HashMap<String, ArrayList<String>> map = new HashMap<>();
	                     map = new HashMap<>();
	                     
	                     // adding each child node to HashMap key => value
	                     map.put(TAG_ID, id);
	                     map.put(TAG_COMPANY, company);
	                     map.put(TAG_NAME, name);
	                     map.put(TAG_COMMENT, comment);
	                     Log.i("All Jobs: ", "check 4");
	                     
	                     // adding HashList to ArrayList
	                     lst.add(map);
	             } 
	                 // no Jobs found
	                 // Launch Add New product Activity
	                
	         } catch (JSONException e) {
	             e.printStackTrace();
	             Log.i("All Jobs: ", "errooror");
	             
	         }
	 		return null;
	 	}
	 	@Override
	 	protected void onPostExecute(String file_url) {
	         // dismiss the dialog after getting all Jobs
	 		dialog.dismiss();
	         // updating UI from Background Thread
	                 /**
	                  * Updating parsed JSON data into ListView
	                  * */
	 		 runOnUiThread(new Runnable() {
	                public void run() {

	             	 Log.i("post: ", "cpost check 1");
	                 /*ListAdapter adapter = new SimpleAdapter(
	                         MainActivity.this, lst,
	                         R.layout.fragment_main, new String[] {TAG_ID,
	                                TAG_COMPANY, TAG_NAME,TAG_COMMENT},
	                         new int[] { R.id.job_id, R.id.company_name,R.id.name,R.id.comment });
	                 // updating listview

	            	 Log.i("post: ", "cpost check 2");
	                 setListAdapter(adapter);*/
	             	/* Fragment fragment= getFragmentManager().findFragmentById(R.id.frag);
	             	 FragmentTransaction trans= getFragmentManager().beginTransaction();
	             	 PlaceholderFragment f= new PlaceholderFragment();
	             	 trans.add(mViewPager.getId(),f,"replacefragment");*/
	             	 
	             	 job_id.setText(map.get(TAG_ID));
	             	 company_name.setText(map.get(TAG_COMPANY));
	             	 name.setText(map.get(TAG_NAME));
	             	 comment.setText(map.get(TAG_COMMENT));
	            	 Log.i("post: ", "cpost check 3");
	                }
	            });
	     }
	 	
	 }
	}
