package com.example.ts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.Plus.PlusOptions;
import com.google.android.gms.plus.model.people.Person;

public class MidActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
	SessionManager session2;
	ImageView g,fb;


private static final int SIGN_IN_REQUEST_CODE = 10;
private static final int ERROR_DIALOG_REQUEST_CODE = 11;
private GoogleApiClient mGoogleApiClient;
private boolean mSignInClicked;
private boolean mIntentInProgress;
private ConnectionResult mConnectionResult;
ConnectionDetector cd;
Boolean isInternetPresent=false;
	String userName,userEmail,result;
	 Animation animFadein,slide,fade_in;
	 TextView txtMessage;
	 ImageView im;
	 Button b1,b2;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mid);
		Log.i("cvd","mid activity");
		 cd = new ConnectionDetector(getApplicationContext());
	  session2 = new SessionManager(getApplicationContext());
	  g=(ImageView)findViewById(R.id.g);	  
	  fb=(ImageView)findViewById(R.id.fb);	  
		g.setOnClickListener(this);
		fb.setOnClickListener(this);
		txtMessage = (TextView) findViewById(R.id.txt);
        slide = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up); 
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in); 
        
      isInternetPresent = cd.isConnectingToInternet();
        
        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests
           
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(MidActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
           
        }
        
        im=(ImageView) findViewById(R.id.imgLogo);
      //  g=(ImageView) findViewById(R.id.g);
       // fb=(ImageView) findViewById(R.id.fb);
//    	g.setOnClickListener(this);
      //  fb.setOnClickListener(this);
		 mGoogleApiClient = buildGoogleAPIClient();
		// g.startAnimation(fade_in);	
      	//fb.startAnimation(fade_in);	 
		
		 new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	im.startAnimation(slide); 
	                 
	            }
	        }, 1000); 

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
	    //  if (mGoogleApiClient.isConnected())
	      //   mGoogleApiClient.disconnect();
	   }
	 
	 
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.g:
				processSignIn();
		         break;
			case R.id.fb:
				Intent i2=new Intent(MidActivity.this,MainActivity2.class);
				startActivity(i2);
				finish();
				break;
			}
		}

		   private void processUIUpdate(boolean isUserSignedIn) {
			      if (isUserSignedIn) {
			    	  session2.createLoginSession(userName, userEmail, "2");
			         Intent i=new Intent(MidActivity.this,Async.class);
			         i.putExtra("name",userName);
						i.putExtra("email",userEmail);
						i.putExtra("type", "gmail");
			         startActivity(i);
			         finish();
			      } 
			   }
			 
			   /**
			    * Handle results for your startActivityForResult() calls. Use requestCode
			    * to differentiate.
			    */
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

			  
				 
				   /**
				    * API to handler sign in of user If error occurs while connecting process
				    * it in processSignInError() api
				    */
				   private void processSignIn() {
				 
				      if (!mGoogleApiClient.isConnecting()) {
				         processSignInError();
				         mSignInClicked = true;
				      }
				 
				   }
				 
				   /**
				    * API to process sign in error Handle error based on ConnectionResult
				    */
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
				    * Callback for GoogleApiClient connection failure
				    */
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
				 
				   /**
				    * Callback for GoogleApiClient connection success
				    */
				   @Override
				   public void onConnected(Bundle connectionHint) {
				      mSignInClicked = false;
				      Toast.makeText(getApplicationContext(), "Signed In Successfully",
				            Toast.LENGTH_LONG).show();
				 
				      processUserInfoAndUpdateUI();
				 
				      processUIUpdate(true);
				 
				   }
				 
				   /**
				    * Callback for suspension of current connection
				    */
				   @Override
				   public void onConnectionSuspended(int cause) {
				      mGoogleApiClient.connect();
				 
				   }
				 
				   /**
				    * API to update signed in user information
				    */
				   private void processUserInfoAndUpdateUI() {
				      Person signedInUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				      if (signedInUser != null) {
				 
				         if (signedInUser.hasDisplayName()) {
				             userName = signedInUser.getDisplayName();
				           Log.e("username", userName);
				         }

				          userEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
				         Log.e("Email: ",userEmail);
				 
				     
				 
				      }
				   }
				  
				
		
}