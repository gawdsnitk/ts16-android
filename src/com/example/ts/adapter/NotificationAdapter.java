package com.example.ts.adapter;

import com.example.ts.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NotificationAdapter extends CursorAdapter {
	  public NotificationAdapter(Context context, Cursor cursor, int flags) {
	      super(context, cursor, 0);
	  }
 
	  @Override
	  public View newView(Context context, Cursor cursor, ViewGroup parent) {
	      return LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
	  }

	  @Override
	  public void bindView(View view, Context context, Cursor cursor) {
	     
	      TextView Noti = (TextView) view.findViewById(R.id.noti);
	      TextView Id = (TextView) view.findViewById(R.id.Iid);
	     
	      String n = cursor.getString(cursor.getColumnIndexOrThrow("notification"));
	      int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
	    
	      Noti.setText(n);
	      Id.setText(String.valueOf(id));
	  }

	}