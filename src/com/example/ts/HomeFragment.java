package com.example.ts;
 
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private static final String TAG = null;
	private GoogleMap googleMap;
	 double mLatitude,mLongitude;
    public HomeFragment(){}
     
  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
          
       try {
            // Loading map
            initilizeMap();
            mLatitude =29.9492574;
    		mLongitude =76.8136831 ;
    		LatLng latLng = new LatLng(mLatitude, mLongitude);
    		
    		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    		googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    		MarkerOptions markerOptions = new MarkerOptions();
    		double lat=29.9477181;
    		double lng=76.814656;
    		LatLng latLng2 = new LatLng(lat, lng);
            markerOptions.position(latLng2);
            markerOptions.title("Comp Department");
            Marker m = googleMap.addMarker(markerOptions);	
    		//electronics
    		double lat1=29.9479876;
    		double lng1=76.8130648;
    		LatLng latLng3 = new LatLng(lat1, lng1);
            markerOptions.position(latLng3);
            markerOptions.title("Electronics Department");	
            m = googleMap.addMarker(markerOptions);	
    		//electrical
    		double lat2=29.947039;
    		double lng2=76.8143071;
    		LatLng latLng4 = new LatLng(lat2, lng2);
            markerOptions.position(latLng4);
            markerOptions.title("Electrical Department");	
            m = googleMap.addMarker(markerOptions);	
    		//civil
    		double lat3=29.9463949;
    		double lng3=76.8140834;
    		LatLng latLng5 = new LatLng(lat3, lng3);
            markerOptions.position(latLng5);
            markerOptions.title("Civil Department");	
            m = googleMap.addMarker(markerOptions);	
    		//mechanical
    		double lat4=29.9467346;
    		double lng4=76.8136563;
    		LatLng latLng6 = new LatLng(lat4, lng4);
            markerOptions.position(latLng6);
            markerOptions.title("Mechanical Department");	
            m = googleMap.addMarker(markerOptions);	
    		//mba block
    		double lat5=29.9452992;
    		double lng5=76.8141814;
    		LatLng latLng7 = new LatLng(lat5, lng5);
            markerOptions.position(latLng7);
            markerOptions.title("MBA Block");	
            m = googleMap.addMarker(markerOptions);	
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            
            
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        
        return rootView;
    }

    @SuppressLint("NewApi") public void onDestroyView() 
    {
    	super.onDestroyView();
    	 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
    		 if (!getActivity().isFinishing())
    		 {
    		 super.onDestroyView(); 
    	       Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
    	       android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
    	       ft.remove(fragment);
    	       ft.commit();
    		 }
    	    } else {
    	    	super.onDestroyView(); 
    	        Fragment fragment = (getChildFragmentManager().findFragmentById(R.id.map));  
    	        android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
    	        ft.remove(fragment);
    	        ft.commit();
    	    }
   }
  
    
private void initilizeMap() {
    if (googleMap == null) {
    	Log.i("surb","gfg");
        googleMap = getMapFragment().getMap();

        // check if map is created successfully or not
        if (googleMap == null) {
            Toast.makeText((Context) getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
@SuppressLint("NewApi") private MapFragment getMapFragment() {
    android.app.FragmentManager fm = null;

    Log.d(TAG, "sdk: " + Build.VERSION.SDK_INT);
    Log.d(TAG, "release: " + Build.VERSION.RELEASE);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        Log.d(TAG, "using getFragmentManager");
        fm = getFragmentManager();
    } else {
        Log.d(TAG, "using getChildFragmentManager");
        fm = getChildFragmentManager();
    }

    return (MapFragment) fm.findFragmentById(R.id.map);
}
private Object getApplicationContext() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void onResume() {
    super.onResume();
    initilizeMap();
}

}