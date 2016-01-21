package com.example.ts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ts.adapter.TabsPagerAdapter;
import com.example.ts.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
 
public class EventsMainActivity extends FragmentActivity implements
        ActionBar.TabListener {
 
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    EventsDbHandler handler;
	SQLiteDatabase db;
	
	String a,b,c,d,e,f,g,h,i;
    // Tab titles
    private String[] tabs = { "Events","Event Description", "Rules", "Details" };
 int event_id;
 String eid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_main_layout);
        String stringData = null;
        Intent intent = getIntent();
        stringData= intent.getStringExtra("p");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        eid=stringData;
        event_id = Integer.parseInt(stringData);
        Log.i("event_id",eid);
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
      //  actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
        handler = new EventsDbHandler(this);
		 db = handler.getReadableDatabase();
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        if(savedInstanceState!=null)
        {
        actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab",0));
        }
        
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
            
         
        });
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
        
        Cursor cursor = db.query(true,handler.TABLE_E, new String[] { handler.KEY_ID,
				handler.KEY_NAME, handler.KEY_DES,handler.KEY_C1,handler.KEY_C2,handler.KEY_P1,handler.KEY_P2,handler.KEY_RULES,handler.KEY_VENUE,handler.KEY_DATE}, handler.KEY_ID + "="+
				eid, null, null, null, null, null);
        Log.i("hey",eid);
		if (cursor != null)
			cursor.moveToFirst();
      
		if(cursor.getCount()==0)
			Log.i("Count is","zero");
       Log.i("fetching","1");
       Log.i("cdvx",cursor.getString(1));
       Log.i("frist",cursor.getString(2));
       a=cursor.getString(1);//_name
       b=cursor.getString(2);//des
       c=cursor.getString(3);//coordinator_1
       d=cursor.getString(4);//c2
       e=cursor.getString(5);//phone_no1
       f=cursor.getString(6);//p2
       g=cursor.getString(7);//rules
       h=cursor.getString(8);//venue
       i=cursor.getString(9);//date
        if(tab.getPosition()==1)
        {
        	//changeText();
        	 new HttpAsyncTask1().execute("http://www.techspardha.org:8081/events/searchEvent");
        }
        
        else if(tab.getPosition()==2)
        {
        	new HttpAsyncTask2().execute("http://www.techspardha.org:8081/events/searchEvent");
        	
        }
        else if(tab.getPosition()==3)
        {
        	new HttpAsyncTask3().execute("http://www.techspardha.org:8081/events/searchEvent");
        	
        }
        
    }
    public void changeText()
    {
  	//  ((F1)mAdapter.getItem(0)).updateTextView(a,b);
  	  TextView tv1=(TextView)findViewById(R.id.eventhead1);
  		//tv1.setTextColor(Color.rgb(17, 17, 45));
  		//Log.i("Chnages","clr");
  	 // Log.i("getting",tv1.getText().toString());
  	  tv1.setText(a,TextView.BufferType.SPANNABLE);
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    	/*int item=viewPager.getCurrentItem();
    	String tag="";
    	if(item==0)
    		tag="fragment1";
    	else if(item==1)
    		tag="fragment2";
    	else if(item==2)
    		tag="fragment3";
    //	Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + ViewPager.getCurrentItem());
    	//Fragment fr=getFragmentManager().findFragmentByTag(tag);
    	//fr.setRetainInstance(true);
    	Fragment f = getChildFragmentManager().getFragments().get(viewPager.getCurrentItem());
    	ft.hide(f);*/
    }
    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
             Log.i("hey","me");
        //    return POST(urls[0],event_id);
             
        return "hey";
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
          /*  Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

	        String s =result;
			JSONObject obj;
			try {
				obj = new JSONObject(s);
		        
		        String detHead = (String)obj.get("nameOfEvent");
		        String detDesc = (String)obj.get("description");
		        */
        	
        	TextView evdet=(TextView)findViewById(R.id.eventdetail1);
		        TextView evhead=(TextView)findViewById(R.id.eventhead1);
		        
		        evhead.setTextColor(Color.rgb(17, 17, 17));
		        evdet.setTextColor(Color.rgb(50, 50, 50));

		        evhead.setText(Html.fromHtml(a), TextView.BufferType.SPANNABLE);
		        evdet.setText(Html.fromHtml(b), TextView.BufferType.SPANNABLE);			       
		//	} 
        //catch (JSONException e) {
			//	e.printStackTrace();
		//	}
            
            
        }
    }
	 
	 private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	             Log.i("hey","me");
	        //    return POST(urls[0],event_id);
	        return "hey";
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	          /*  Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

		        String s =result;
				JSONObject obj;
				try {
					obj = new JSONObject(s);
			        
			        String detHead = (String)obj.get("nameOfEvent");
			        String detDesc = (String)obj.get("description");
			        */
			        TextView evdet=(TextView)findViewById(R.id.eventdetail2);
			        TextView evhead=(TextView)findViewById(R.id.eventhead2);
			        
			        evhead.setTextColor(Color.rgb(17, 17, 17));
			        evdet.setTextColor(Color.rgb(50, 50, 50));

			       // evhead.setText(Html.fromHtml(a), TextView.BufferType.SPANNABLE);
			        evdet.setText(Html.fromHtml(g), TextView.BufferType.SPANNABLE);			       
			//	} 
	        //catch (JSONException e) {
				//	e.printStackTrace();
			//	}
	            
	            
	        }
	    }
	 
	 private class HttpAsyncTask3 extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	             Log.i("hey","me");
	        //    return POST(urls[0],event_id);
	        return "hey";
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	          /*  Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

		        String s =result;
				JSONObject obj;
				try {
					obj = new JSONObject(s);
			        
			        String detHead = (String)obj.get("nameOfEvent");
			        String detDesc = (String)obj.get("description");
			        */
			        TextView evdet=(TextView)findViewById(R.id.eventdetail);
			        TextView evhead=(TextView)findViewById(R.id.eventhead);
			        
			        evhead.setTextColor(Color.rgb(17, 17, 17));
			        evdet.setTextColor(Color.rgb(50, 50, 50));

			        evhead.setText(c+" "+e, TextView.BufferType.SPANNABLE);
			        evdet.setText(d+" "+f, TextView.BufferType.SPANNABLE);			       
			//	} 
	        //catch (JSONException e) {
				//	e.printStackTrace();
			//	}
	            
	            
	        }
	    }
	 
	 public static String POST(String url,int evid){
	       InputStream inputStream = null;
	       String result = "";
	       try {
	           HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httpPost = new HttpPost(url);
	           List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	           nameValuePairs.add(new BasicNameValuePair("eventId",Integer.toString(evid)));
	           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	           // 7. Set some headers to inform server about the type of the content
	           httpPost.setHeader("Accept", "application/json");
            
	           // 8. Execute POST request to the given URL
	           HttpResponse httpResponse = httpclient.execute(httpPost);


	           // 9. receive response as inputStream
	           inputStream = httpResponse.getEntity().getContent();

	           // 10. convert inputstream to string
	           if(inputStream != null)
	               result = convertInputStreamToString(inputStream);
	           else
	               result = "Did not work!";

	       } catch (Exception e) {
	           Log.d("InputStream", "" + e.getMessage());
	       }
	     Log.i("surbhi",result);
	     
	     
	       // 11. return result
	       return result;
	   }
	 
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        
	        Log.i("surbhi234",result);
	        return result;
	 
	    }
	 
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	     // Respond to the action bar's Up/Home button
	     case android.R.id.home:
	        //NavUtils.navigateUpFromSameTask(this);
	    	// Intent i=new Intent(Competitions.this,HomeActivity.class);
	    	 //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	// startActivity(i);
	    	 finish();
	         	    	 return true;
	     }
	     return super.onOptionsItemSelected(item);
	 }
 
}