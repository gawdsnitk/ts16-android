package com.example.ts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class F1 extends Fragment {
 TextView mTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.layout_f1, container, false);
        mTextView=(TextView) rootView.findViewById(R.id.eventdetail1);
        return rootView;
    }
    public TextView getTextView1(){
        return mTextView;
    }
}