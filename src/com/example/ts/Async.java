package com.example.ts;

import static com.example.ts.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.ts.CommonUtilities.EXTRA_MESSAGE;
import static com.example.ts.CommonUtilities.SENDER_ID;
import static com.example.ts.CommonUtilities.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
public class Async extends Activity{
	static String name;
	static String email;
	String type;
	String result;
	TextView lblMessage;
	AsyncTask<Void, Void, Void> mRegisterTask;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  lblMessage = (TextView) findViewById(R.id.lblMessage);
	  
	  Intent i = getIntent();
	  name = i.getStringExtra("name");
		email = i.getStringExtra("email");
		type = i.getStringExtra("type");
		new Login().execute();
	 }
	 class Login extends AsyncTask<Void,Void,Void>
		{

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				 try{
				       String link = "http://192.168.43.122/loginn.php?email="+email+"&username="+name+"&type="+type;  
				        // URL url = new URL(link);
				         HttpClient client = new DefaultHttpClient();
				         HttpGet request = new HttpGet();
				         Log.e("go and","die");
				         Log.e("email",email);
				         request.setURI(new URI(link));
				         Log.e("Before","execution");
				      
				         HttpResponse response = client.execute(request);
				         Log.e("is someting","happening");
				         BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				         Log.e("Query","Executed");
				           StringBuffer sb = new StringBuffer("");
				           String line="";
				           
				           while ((line = in.readLine()) != null) {
				        	   Log.e("response","came");
				              sb.append(line);
				              break;
				            }
				            in.close();
				            result=sb.toString();
				            Log.e("Coming","here");
				            Log.e("check2",result);
				         }
				         
				         catch(Exception e){
				             new String("Exception: " + e.getMessage());
				         }
				return null;
			}
			protected void onPostExecute(Void para)
			{
				AlertDialogManager alert = new AlertDialogManager();
				ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
				
				if (!cd.isConnectingToInternet()) {
					alert.showAlertDialog(Async.this,
							"Internet Connection Error",
							"Please connect to working Internet connection", false);
					return;
				}

				if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
						|| SENDER_ID.length() == 0) {
					alert.showAlertDialog(Async.this, "Configuration Error!",
							"Please set your Server URL and GCM Sender ID", false);
					
					 return;
				}
				RegisterWithGCM();
				      Intent i=new Intent(Async.this,HomeActivity.class);
				      startActivity(i);
				      finish();
			}
			
		}
	 public void RegisterWithGCM()
		{
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			
			Log.i("inside","gcm");
	        lblMessage = (TextView) findViewById(R.id.lblMessage);
			
			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));
			
			final String regId = GCMRegistrar.getRegistrationId(this);
			if (regId.equals("")) {
			
				GCMRegistrar.register(this, SENDER_ID);
			} else {

				if (GCMRegistrar.isRegisteredOnServer(this)) {
					
					Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
				} else {
					final Context context = this;
					mRegisterTask = new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {
							ServerUtilities.register(context, name, email, regId);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;
						}

					};
					mRegisterTask.execute(null, null, null);
				}
			}
		}		

		
		private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
				WakeLocker.acquire(getApplicationContext());
			
				lblMessage.append(newMessage + "\n");			
				Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
				
				WakeLocker.release();
			}
		};
}