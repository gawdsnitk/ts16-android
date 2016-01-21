package com.example.ts.adapter;

import com.example.ts.F1;
import com.example.ts.F2;
import com.example.ts.F3;
import com.example.ts.F4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new F4();
        case 1:
            // Games fragment activity
            return new F1();
        case 2:
            // Movies fragment activity
            return new F2();
        case 3:
            // Movies fragment activity
            return new F3();
            
            
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
 
}