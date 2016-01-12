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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ts.adapter.BaseInflaterAdapter;
import com.example.ts.adapter.CardItemData;
import com.example.ts.adapter.inflaters.CardInflater;

public class Competitions extends Activity implements OnItemClickListener
{    int pos;
     private Activity activity;
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		ListView list = (ListView)findViewById(R.id.list_view);
		 list.setOnItemClickListener((OnItemClickListener) this);
		list.addHeaderView(new View(this));
		list.addFooterView(new View(this));

		BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());
		String text1="MANAGERIAL";
		String text2="QUIZZES";
		String text3="FUN ZONE";
		String text4="ONLINE";
		String text5="PAPYRUS VITAE";
		String text6="TECHNOPOLIS";
		String text7="DESIGN";
		
		
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
		adapter.addItem(data6, false);
		

		list.setAdapter(adapter);
		
}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		   pos=position;
			new HttpAsyncTask().execute("http://www.techspardha.org/category_event");
		
		
		
	}
	
	 private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	             Log.i("hey","me");
	            return POST(urls[0],pos);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
	          //  activity.startActivity(new Intent(activity,Events.class));
	            Intent intent_name = new Intent();
	            intent_name.putExtra("rslt", result);
	            intent_name.setClass(getApplicationContext(),Events.class);
	            startActivity(intent_name);
	        }
	    }
	 
	 
	 public static String POST(String url,int pos){
	       InputStream inputStream = null;
	       String result = "";
	       try {

	           HttpClient httpclient = new DefaultHttpClient();
	           HttpPost httpPost = new HttpPost(url);

	           // 3. build jsonObject
	           // 4. convert JSONObject to JSON to String
	           // 5. set json to StringEntity
	           // 6. set httpPost Entity


	           List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	           nameValuePairs.add(new BasicNameValuePair("category_id",Integer.toString(pos)));
	           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	           // 7. Set some headers to inform server about the type of the content
	           httpPost.setHeader("Accept", "application/json");
	           //httpPost.setHeader("Content-type", "application/json");
	//removed because the request's content type is not json
                
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
	           Log.d("InputStream", e.getLocalizedMessage());
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
