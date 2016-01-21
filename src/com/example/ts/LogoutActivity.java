package com.example.ts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.PlusOptions;
public class LogoutActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener{
	Button logout;
	SessionManager session2;
	ConnectionDetector cd;
	Boolean isInternetPresent=false;
	private static final int SIGN_IN_REQUEST_CODE = 10;
	   private static final int ERROR_DIALOG_REQUEST_CODE = 11;
	   // For communicating with Google APIs
	   private GoogleApiClient mGoogleApiClient;
	   private boolean mSignInClicked;
	   private boolean mIntentInProgress;
	   // contains all possible error codes for when a client fails to connect to
	   // Google Play services
	   private ConnectionResult mConnectionResult;

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	 // setContentView(R.layout.activity_logout);
	  session2 = new SessionManager(getApplicationContext()); 
	  cd = new ConnectionDetector(getApplicationContext());
	  isInternetPresent = cd.isConnectingToInternet();
	          
	          // check for Internet status
	          if (isInternetPresent) {
	              // Internet Connection is Present
	              // make HTTP requests
	             
	          } else {
	              // Internet connection is not present
	              // Ask user to connect to Internet
	              showAlertDialog(LogoutActivity.this, "No Internet Connection",
	                      "You don't have internet connection.", false);
	             
	          }
	          
	 // logout = (Button) findViewById(R.id.logoutButton);
	  mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Plus.API, PlusOptions.builder().build())
	            .addScope(Plus.SCOPE_PLUS_LOGIN).build();//buildGoogleAPIClient();
	  /*logout.setOnClickListener(new OnClickListener() {

	   @Override
	   public void onClick(View v) {
		   */
		   if(session2.getlog().equals("1"))
		   {
	    if (MainActivity2.currentSession != null) {
	     MainActivity2.currentSession.closeAndClearTokenInformation();
	     
	    }
	    session2.logoutUser();
	     Intent i=new Intent(LogoutActivity.this,StartActivity.class);
	     startActivity(i);
	     finish();
		   }
	/*	   else if(session2.getlog().equals("2"))
		   {
			   processSignOut();
		   }
		*/  
	 //  }
	 // });
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
	 private GoogleApiClient buildGoogleAPIClient() {
	      return new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Plus.API, PlusOptions.builder().build())
	            .addScope(Plus.SCOPE_PLUS_LOGIN).build();
	   }
	 
	  @Override
	   protected void onStart() {
	      super.onStart();
	      // make sure to initiate connection
	      mGoogleApiClient.connect();
	      
	   }
	 
	   @Override
	   protected void onStop() {
	      super.onStop();
	      // disconnect api if it is connected
	      if (mGoogleApiClient.isConnected())
	         mGoogleApiClient.disconnect();
	   }
	   @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      if (requestCode == SIGN_IN_REQUEST_CODE) {
	         if (resultCode != RESULT_OK) {
	            mSignInClicked = false;
	         }
	         mIntentInProgress = false;
	 
	         if (!mGoogleApiClient.isConnecting()) {
	            mGoogleApiClient.connect();
	         }
	      } 
	   }

	  
		   private void processSignOut() {
		      if (mGoogleApiClient.isConnected()) {
		         Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
		         mGoogleApiClient.disconnect();
		         mGoogleApiClient.connect();
		         processUIUpdate(false);
		      }
		 
		   }
		   
		   @Override
		   public void onConnectionFailed(ConnectionResult result) {
		      if (!result.hasResolution()) {
		         GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
		               ERROR_DIALOG_REQUEST_CODE).show();
		         return;
		      }
		      if (!mIntentInProgress) {
		         mConnectionResult = result;
		 
		         if (mSignInClicked) {
		            processSignInError();
		         }
		      }
		 
		   }
		   private void processUIUpdate(boolean isUserSignedIn) {
			      if (!isUserSignedIn) {
			    	  session2.logoutUser();
			         Intent i=new Intent(LogoutActivity.this,StartActivity.class);
			         startActivity(i);
			         finish();
			      }
			   }
		   
		   /**
		    * Callback for GoogleApiClient connection success
		    */
		   @Override
		   public void onConnected(Bundle connectionHint) {
		      mSignInClicked = false;
		    //  Toast.makeText(getApplicationContext(), "Signed In Successfully",
		     //       Toast.LENGTH_LONG).show();
		   //   if(session2.getlog().equals("2"))
			//   {
				   processSignOut();
			//   }
		 
		   }
		   private void processSignInError() {
			      if (mConnectionResult != null && mConnectionResult.hasResolution()) {
			         try {
			            mIntentInProgress = true;
			            mConnectionResult.startResolutionForResult(this,
			                  SIGN_IN_REQUEST_CODE);
			         } catch (SendIntentException e) {
			            mIntentInProgress = false;
			            mGoogleApiClient.connect();
			         }
			      }
			   }
		   /**
		    * Callback for suspension of current connection
		    */
		   @Override
		   public void onConnectionSuspended(int cause) {
		      mGoogleApiClient.connect();
		 
		   }
}