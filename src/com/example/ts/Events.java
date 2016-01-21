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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ts.adapter.BaseInflaterAdapter;
import com.example.ts.adapter.CardItemData;
import com.example.ts.adapter.inflaters.CardInflater;

public class Events extends Activity implements OnItemClickListener
{
	ProgressDialog loading = null;
	int event_id;
	EventsDbHandler handler;
	SQLiteDatabase db;
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	String id1,text;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comp);
		 handler = new EventsDbHandler(this);
		 db = handler.getWritableDatabase();
		//	getActionBar().setDisplayHomeAsUpEnabled(true);
		loading = new ProgressDialog(this);
		loading.setCancelable(true);
		loading.setMessage("Please wait....");
		loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			
		ListView list = (ListView)findViewById(R.id.list_view);
		 list.setOnItemClickListener((OnItemClickListener) this);
		list.addHeaderView(new View(this));
		list.addFooterView(new View(this));
		cd = new ConnectionDetector(getApplicationContext());
isInternetPresent = cd.isConnectingToInternet();
        
        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
           
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(Events.this, "No Internet Connection",
                    "You don't have internet connection.", false);
           
        }
        
        
		BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
		Bundle bundle = getIntent().getExtras();
		String r = bundle.getString("rslt");
		int pos=bundle.getInt("value");
		ImageView imm=(ImageView) findViewById(R.id.image_view);
		switch(pos)
		{
		case 1:
			text="Managerial";
			imm.setBackgroundResource(R.drawable.a1);
			break;
		case 2:
			imm.setBackgroundResource(R.drawable.b1);
			text="Quizzes";
			break;
		case 3:
			text="Fun Zone";
			imm.setBackgroundResource(R.drawable.c1);
			break;
		case 4:
			imm.setBackgroundResource(R.drawable.d1);
			text="Online";
			break;
		case 5:
			text="Papyrus Vitae";
			imm.setBackgroundResource(R.drawable.e1);
			break;
		case 6:
			text="Technopolis";
			imm.setBackgroundResource(R.drawable.f1);
			break;
		case 7:
			text="Design";
			imm.setBackgroundResource(R.drawable.g1);
			break;
		case 8:
			text="Brain Storming";
			imm.setBackgroundResource(R.drawable.h1);
			break;
		case 9:
			text="Future Builder";
			imm.setBackgroundResource(R.drawable.i1);
		}
		
		//imm.setImageResource(R.drawable.g1);
		
		TextView txt=(TextView) findViewById(R.id.textView1);
		txt.setText(text);
		//Log.i("plzzz",r);
		JSONArray newJArray = null;
		try {
			 newJArray = new JSONArray(r);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < newJArray.length(); i++) {
		    JSONObject jsonobject = null;
			try {
				jsonobject = newJArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String name = null;
			try {
				name = jsonobject.getString("nameOfEvent");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    int id = 0 ;
			try {
				id = jsonobject.getInt("_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CardItemData data1 = new CardItemData(name,id);
			adapter.addItem(data1, false);
		   
		}
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
		
		CardItemData card = (CardItemData)parent.getItemAtPosition(position);
		event_id=card.id();
		Log.i("On item ","clicked in events");
		Cursor cursor = db.query(true,handler.TABLE_E, new String[] { handler.KEY_ID,
				handler.KEY_NAME, handler.KEY_DES}, handler.KEY_ID + "="+
				event_id, null, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		
		if(cursor.getCount()==0)
		{
		new HttpAsyncTask1().execute("http://manage.techspardha.org/events/searchEvent");
		Log.i("On item ","clicked2");
		/* Intent intent = new Intent(this, EventsMainActivity.class);
		 intent.putExtra("p",id1);
   		startActivity(intent);*/
		}
		
		else
		{Log.i("In pass",cursor.getString(1));
			pass();
		}
	}

	 private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
		 @Override
		 protected void onPreExecute() {    
		     // SHOW THE SPINNER WHILE LOADING FEEDS
		    loading.show();
		 }
		 
		 @Override
	        protected String doInBackground(String... urls) {
	             Log.i("hey","me");
	            return POST(urls[0],event_id);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	           // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
              loading.dismiss();
		        String s =result;
		        Log.i("final s",s);
				try {
					Log.i("one","1");
					JSONObject obj = new JSONObject(s);
					Log.i("two",obj.toString());
					     id1=obj.getString("_id");
					    Log.i("three",id1);
					    String name = obj.getString("nameOfEvent");
					    String des = obj.getString("description");
					    String c1 = obj.getString("coordinator_1");
					    String c2 = obj.getString("coordinator_2");
					    String p1 = obj.getString("phoneno_1");
					    String p2 = obj.getString("phoneno_2");
					    String rules = obj.getString("rules");
					    String venue = obj.getString("venue");
					    String date = obj.getString("dateOfEvent");
					    
					    
					    String str_des=stripHtml(des);
					    String str_c1=stripHtml(c1);
					    String str_c2=stripHtml(c2);
					    String str_p1=stripHtml(p1);
					    String str_p2=stripHtml(p2);
					    String str_rules=stripHtml(rules);
					    String str_date=stripHtml(date);
					    String str_venue=stripHtml(venue);
					    
			        ContentValues values=new ContentValues();
			              values.put("_id",id1);
			              values.put("_name",name);	
			              values.put("des",str_des);	
			              values.put("coordinator_1",str_c1);
			              values.put("coordinator_2",str_c2);
			              values.put("phoneno_1",str_p1);
			              values.put("phoneno_2",str_p2);
			              values.put("rules",str_rules);
			              values.put("date",str_date);
			              values.put("venue",str_venue);
			             
			              db.insert("events",null,values);
			             // db.insertData(id,name,des);
			             
			       	     
				} catch (JSONException e) {
					e.printStackTrace();
				}
	            
				/*Log.i(" back","here");
				db = handler.getReadableDatabase();
				
				Cursor cursor = db.rawQuery("SELECT  * FROM events", null);
				if(cursor==null)
					Log.i("null","here");
				if (cursor != null)
					{cursor.moveToFirst();
		             Log.i("not","null");
					}Log.i("print","dfg");
					if(cursor.getCount()>0)
						Log.i("greater than 0","dfgvdf");
					else
						Log.i("not","greater");

					
					Log.i(cursor.getString(1),"lctfrvg");*/
					pass();
			   		
	        }
	    }
	 public String stripHtml(String html) {
		    return Html.fromHtml(html).toString();
		}
	 
	 public void pass()
	 {

		 Intent intent = new Intent(this, EventsMainActivity.class);
		 Log.i("sending",String.valueOf(event_id));
		 intent.putExtra("p",String.valueOf(event_id));
   		startActivity(intent);
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
	 /*
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
*/
}
