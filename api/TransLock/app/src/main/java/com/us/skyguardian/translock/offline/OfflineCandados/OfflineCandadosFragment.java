package com.us.skyguardian.translock.offline.OfflineCandados;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.offline.OfflineViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineCandadosFragment extends Fragment {

    RecyclerView candados;
    OfflineCandadosAdapter offlineCandadosAdapter;
    OfflineViewModel offlineViewModel;
    public OfflineCandadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline_candados, container, false);
        offlineViewModel = ViewModelProviders.of(getActivity()).get(OfflineViewModel.class);
        ArrayList<Candado> listaCandados = offlineViewModel.getListaCandadosOffiline().getValue();
        offlineCandadosAdapter = new OfflineCandadosAdapter(listaCandados);
        candados = view.findViewById(R.id.candados_offline);
        candados.setLayoutManager(new LinearLayoutManager(getContext()));
        candados.setAdapter(offlineCandadosAdapter);

        for (Candado candado : listaCandados) {
            Log.i("OFFLINE", candado.alias);
        }
        return view;
    }

}
