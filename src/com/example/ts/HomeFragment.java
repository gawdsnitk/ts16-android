package com.example.ts;
 
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
 
public class HomeFragment extends Fragment {
	 public GoogleMap googleMap;
    public HomeFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        /*try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 */
        
        return rootView;
    }
    
    
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
               /* Toast.makeText(
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();*/
            }
        }
    }
 
    @Override
	public void onResume() {
        super.onResume();
        initilizeMap();
    }
    
}