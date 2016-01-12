package com.example.ts;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
    static final String SERVER_URL = "http://172.16.177.227/gcm_server_php/register.php"; 

    static final String SENDER_ID = "132975867821"; 

    static final String TAG = "TECHSPARDHA";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
