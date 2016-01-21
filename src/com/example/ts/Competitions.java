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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.ts.adapter.BaseInflaterAdapter;
import com.example.ts.adapter.CardItemData;
import com.example.ts.adapter.inflaters.CardInflater;

public class Competitions extends Activity implements OnItemClickListener
{    int pos;
     private Activity activity;
	/**
	 * Called when the activity is first created.
	 */
     ProgressDialog loading = null;
     ConnectionDetector cd;
     Boolean isInternetPresent=false;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ListView list = (ListView)findViewById(R.id.list_view);
		 list.setOnItemClickListener((OnItemClickListener) this);
		list.addHeaderView(new View(this));
		list.addFooterView(new View(this));
		 cd = new ConnectionDetector(getApplicationContext());
		loading = new ProgressDialog(this);
		loading.setCancelable(true);
		loading.setMessage("Please wait....");
		loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		
		
		BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
		String text1="Managerial";
		String text2="Quizzes";
		String text3="Fun Zone";
		String text4="Online";
		String text5="Papyrus Vitae";
		String text6="Technopolis";
		String text7="Design";
		String text8="Brain Storming";
		String text9="Future Builder";
		
		
		 isInternetPresent = cd.isConnectingToInternet();
	        
	        // check for Internet status
	        if (isInternetPresent) {
	            // Internet Connection is Present
	            // make HTTP requests
	           
	        } else {
	            // Internet connection is not present
	            // Ask user to connect to Internet
	            showAlertDialog(Competitions.this, "No Internet Connection",
	                    "You don't have internet connection.", false);
	           
	        }
	        
		CardItemData data1 = new CardItemData(text1,1);
		adapter.addItem(data1, false);
		
		
		CardItemData data2 = new CardItemData(text2,2);
		adapter.addItem(data2, false);
		
		
		CardItemData data3 = new CardItemData(text3,3);
		adapter.addItem(data3, false);
		 
		
		CardItemData data4 = new CardItemData(text4,4);
		adapter.addItem(data4, false);
		
		
		CardItemData data5 = new CardItemData(text5,5);
		adapter.addItem(data5, false);
		
		
		CardItemData data6 = new CardItemData(text6,6);
		adapter.addItem(data6, false);
		CardItemData data7 = new CardItemData(text7,7);
		adapter.addItem(data7, false);
		CardItemData data8 = new CardItemData(text8,7);
		adapter.addItem(data8, false);
		CardItemData data9 = new CardItemData(text9,7);
		adapter.addItem(data9, false);
		list.setAdapter(adapter);
		
}
	
	 public void showAlertDialog(Context context, String title, String message, Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	 
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	         
	        // Setting alert dialog icon
	        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
	 
	        // Setting OK Button
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	 finish();
	            }
	            
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }
	 
	 

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		  
		   pos=position;
		   Log.d("ADebugTag", "Value: " + Float.toString(pos));
		  // Log.d("pos", pos);
			new HttpAsyncTask().execute("http://www.techspardha.org:8081/events/eventByCategoryId");
	}
	
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		 
		 @Override
		 protected void onPreExecute() {    
		     // SHOW THE SPINNER WHILE LOADING FEEDS
		    loading.show();
		 }
		 
	        @Override
	        protected String doInBackground(String... urls) {
	             //Log.i("hey","me");
	            String s=POST(urls[0],pos);
	            //Log.i("s=",s);
	             return s;
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
	          //  activity.startActivity(new Intent(activity,Events.class));
	           // Log.i("comp","comp");
	        	loading.dismiss();
	            Intent intent_name = new Intent();
	            intent_name.putExtra("rslt", result);
	            intent_name.putExtra("value", pos);
	            intent_name.setClass(getApplicationContext(),Events.class);
	            startActivity(intent_name);
	        }
	    }
	 
	 
	 public static String POST(String url,int pos){
	       InputStream inputStream = null;
	       String result = "";
	       try {
           //Log.i("here","threr");
	           HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httpPost = new HttpPost(url);
	           List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	           nameValuePairs.add(new BasicNameValuePair("categoryId",Integer.toString(pos)));
	           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           httpPost.setHeader("Accept", "application/json");
	           HttpResponse httpResponse = httpclient.execute(httpPost);
	           inputStream = httpResponse.getEntity().getContent();
	           if(inputStream != null)
	               result = convertInputStreamToString(inputStream);
	           else
	               result = "Did not work!";

	       } catch (Exception e) {
	           Log.d("InputStream", e.getLocalizedMessage());
	       }
	     //Log.i("surbhi",result);
	       return result;
	   }
	 
	 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	        inputStream.close();
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
