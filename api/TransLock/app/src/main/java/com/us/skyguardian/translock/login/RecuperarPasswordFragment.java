package com.us.skyguardian.translock.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.us.skyguardian.translock.R;


public class RecuperarPasswordFragment extends Fragment {

    public RecuperarPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Navigation.findNavController(getActivity(), R.id.nav_fragment_login).popBackStack();
        return inflater.inflate(R.layout.fragment_recuperar_password, container, false);
    }



}
