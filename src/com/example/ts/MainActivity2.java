package com.example.ts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class MainActivity2 extends Activity {
 private Session.StatusCallback sessionStatusCallback;
 public static  Session currentSession;
SessionManager session2;
 //private Button login;
 String name,email,result;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
 // setContentView(R.layout.activity_main);
  
  session2 = new SessionManager(getApplicationContext());   
  // create instace for sessionStatusCallback
  sessionStatusCallback = new Session.StatusCallback() {

   @Override
   public void call(Session session, SessionState state,
     Exception exception) {
    onSessionStateChange(session, state, exception);

   }
   
  };
  connectToFB();
  /*
  // login button
  login = (Button) findViewById(R.id.loginButton);
  login.setOnClickListener(new OnClickListener() {

   @Override
   public void onClick(View v) {
    connectToFB();
    }
  });

  // logout button
  
  logout = (Button) findViewById(R.id.logoutButton);
  logout.setOnClickListener(new OnClickListener() {

   @Override
   public void onClick(View v) {
    if (currentSession != null) {
     currentSession.closeAndClearTokenInformation();
     logout.setVisibility(View.GONE);
     login.setVisibility(View.VISIBLE);
     session2.logoutUser();
    }
   }
  });
  
  */
 }

 public void connectToFB() {

	  List<String> permissions = new ArrayList<String>();
	  permissions.add("email");

	  currentSession = new Session.Builder(this).build();
	  currentSession.addCallback(sessionStatusCallback);

	  Session.OpenRequest openRequest = new Session.OpenRequest(
	    MainActivity2.this);
	  openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
	  openRequest.setRequestCode(Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
	  openRequest.setPermissions(permissions);
	  currentSession.openForRead(openRequest);
	
	 }

	 /**
	  * this method is used by the facebook API
	  */
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  if (currentSession != null) {
	   currentSession
	     .onActivityResult(this, requestCode, resultCode, data);
	  }
	 }
	 private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
		new Request.GraphUserCallback() {
		@Override
		public void onCompleted(GraphUser user, Response response) {
		// If the response is successful
		if (session == currentSession) {
		if (user != null) {
		Session.setActiveSession(currentSession);
		Log.e("hey", user.toString());
		Log.e("email", user.getProperty("email").toString());
		name=user.getName();
		email=user.getProperty("email").toString();
		session2.createLoginSession(name,email,"1");
		 Intent i=new Intent(MainActivity2.this,Async.class);
		 i.putExtra("name",name);
			i.putExtra("email",email);
			i.putExtra("type", "facebook");
		 startActivity(i);
		 finish();
		}
		}
		if (response.getError() != null) {
		// Handle errors, will do so later.
		}
		}
		});
		Bundle params = request.getParameters();
		params.putString("fields", "email,name");
		request.setParameters(params);
		request.executeAsync();
		}

 private void onSessionStateChange(Session session, SessionState state,
   Exception exception) {
	 if(state.isOpened())
	 {
		 makeMeRequest(currentSession);
		 
		 }
  if (session != currentSession) {
   return;
  }

  if (state.isOpened()) {
   // Log in just happened.
   Toast.makeText(getApplicationContext(), "session opened",
     Toast.LENGTH_SHORT).show();
  } else if (state.isClosed()) {
   // Log out just happened. Update the UI.
   Toast.makeText(getApplicationContext(), "session closed",
     Toast.LENGTH_SHORT).show();
  }
 }
 
}