package com.example.ts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {
	SessionManager session;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  session = new SessionManager(getApplicationContext()); 
	  if(session.isLoggedIn())
	  {
		  Intent i=new Intent(StartActivity.this,HomeActivity.class);
		  startActivity(i);
		  finish();
	  }
	  else
	  {
		  Intent i=new Intent(StartActivity.this,MidActivity.class);
		  startActivity(i);
		  finish();
	  }
	 }
}