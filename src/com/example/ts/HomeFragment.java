package com.example.ts;
 
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private GoogleMap googleMap;
    public HomeFragment(){}
     
  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
          
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        
        return rootView;
    }

    public void onDestroyView() 
    {
       super.onDestroyView(); 
       Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
       android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
       ft.remove(fragment);
       ft.commit();
   }
  
    
private void initilizeMap() {
    if (googleMap == null) {
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();

        // check if map is created successfully or not
        if (googleMap == null) {
            Toast.makeText((Context) getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }
    }
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