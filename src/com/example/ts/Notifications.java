package com.example.ts;

import static com.example.ts.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.ts.CommonUtilities.EXTRA_MESSAGE;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ts.adapter.NotificationAdapter;
import com.google.android.gcm.GCMRegistrar;

public class Notifications extends Activity {
	TextView lblMessage;
	AsyncTask<Void, Void, Void> mRegisterTask;
	AlertDialogManager alert = new AlertDialogManager();
	ConnectionDetector cd;
	
	public static String name;
	public static String email;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_noti);
		cd = new ConnectionDetector(getApplicationContext());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (!cd.isConnectingToInternet()) {
		
			alert.showAlertDialog(Notifications.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			
			return;
		}
		DataBaseHandler handler = new DataBaseHandler(this);
		SQLiteDatabase db = handler.getWritableDatabase();
		Cursor todoCursor = db.rawQuery("SELECT  * FROM notifications", null);
		
		ListView lvItems = (ListView) findViewById(R.id.lvItems);
		
		NotificationAdapter notiAdapter = new NotificationAdapter(this, todoCursor, 0);
		
		lvItems.setAdapter(notiAdapter);
         
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
			DISPLAY_MESSAGE_ACTION));
		
}		

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			lblMessage.append(newMessage + "\n");	
			lblMessage.append("hello"+"\n");
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			WakeLocker.release();
		}
	};
	
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	     // Respond to the action bar's Up/Home button
	     case android.R.id.home:
	        //NavUtils.navigateUpFromSameTask(this);
	    	 Intent i=new Intent(Notifications.this,HomeActivity.class);
	    	 //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(i);
	    	 finish();
	         	    	 return true;
	     }
	     return super.onOptionsItemSelected(item);
	 }
	 
}
