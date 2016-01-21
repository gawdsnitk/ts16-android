package com.example.ts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class LoginSplashActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
	SessionManager session2,session;
	
String userName,userEmail,result;
	 Animation animFadein,slide,fade_in;
	 TextView txtMessage;
	 ImageView im,g,fb;
	 Button b1,b2;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
	  session2 = new SessionManager(getApplicationContext());
		txtMessage = (TextView) findViewById(R.id.txt);
        slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up); 
     	 
        im=(ImageView) findViewById(R.id.imgLogo);
		 new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	
	                session = new SessionManager(getApplicationContext()); 
	                Intent i=new Intent(LoginSplashActivity.this,HomeActivity.class);
	        		  startActivity(i);
	        		  finish();
	            }
	        }, 1200); 

	 }
	@Override
	public void onConnectionFailed(@NonNull ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(@Nullable Bundle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
}