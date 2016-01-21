package com.example.ts;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class EventsPage extends Activity  {
	int event_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventpage);
		
        String stringData = null;
        Intent intent = getIntent();
        if (null != intent) {
        	stringData= intent.getStringExtra("event_id");
        }
        event_id = Integer.parseInt(stringData);
        new HttpAsyncTask().execute("http://www.techspardha.org:3000/events/searchEvent");
	}
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	             Log.i("hey","me");
	            return POST(urls[0],event_id);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();

		        String s =result;
				JSONObject obj;
				try {
					obj = new JSONObject(s);
			        
			        String detHead = (String)obj.get("nameOfEvent");
			        String detDesc = (String)obj.get("description");
			        
			        TextView evdet=(TextView)findViewById(R.id.eventdetail);
			        TextView evhead=(TextView)findViewById(R.id.eventhead);
			        
			        evhead.setTextColor(Color.rgb(17, 17, 17));
			        evdet.setTextColor(Color.rgb(50, 50, 50));

			        evhead.setText(Html.fromHtml(detHead), TextView.BufferType.SPANNABLE);
			        evdet.setText(Html.fromHtml(detDesc), TextView.BufferType.SPANNABLE);			       
				} catch (JSONException e) {
					e.printStackTrace();
				}
	            
	            
	        }
	    }
	 
	 
	 public static String POST(String url,int evid){
	       InputStream inputStream = null;
	       String result = "";
	       try {

	           // 1. create HttpClient
	           HttpClient httpclient = new DefaultHttpClient();

	           // 2. make POST request to the given URL
	           HttpPost httpPost = new HttpPost(url);

	           // 3. build jsonObject
	           // 4. convert JSONObject to JSON to String
	           // 5. set json to StringEntity
	           // 6. set httpPost Entity


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
    
    
    
}
