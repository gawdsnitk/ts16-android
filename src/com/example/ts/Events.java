package com.example.ts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.ts.adapter.BaseInflaterAdapter;
import com.example.ts.adapter.CardItemData;
import com.example.ts.adapter.inflaters.CardInflater;

public class Events extends Activity implements OnItemClickListener
{
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
		Bundle bundle = getIntent().getExtras();
		String r = bundle.getString("rslt");
		//Log.i("plzzz",r);
		JSONArray newJArray = null;
		try {
			 newJArray = new JSONArray(r);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < newJArray.length(); i++) {
		    JSONObject jsonobject = null;
			try {
				jsonobject = newJArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String name = null;
			try {
				name = jsonobject.getString("event_name");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    int id = 0 ;
			try {
				id = jsonobject.getInt("event_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CardItemData data1 = new CardItemData(name,id);
			adapter.addItem(data1, false);
		   
		}
	
		list.setAdapter(adapter);
		
}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		CardItemData card = (CardItemData)parent.getItemAtPosition(position);
		
		
		
		Intent intent = new Intent(this, Eventpage.class);
		intent.putExtra("event_id", String.valueOf(card.id()));
		startActivity(intent);
		
		
	}
}
