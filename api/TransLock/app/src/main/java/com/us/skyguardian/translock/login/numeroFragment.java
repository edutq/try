package com.us.skyguardian.translock.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.us.skyguardian.translock.R;


public class numeroFragment extends Fragment {

    public numeroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numero, container, false);
        // Inflate the layout for this fragment
        return view;
    }

}
