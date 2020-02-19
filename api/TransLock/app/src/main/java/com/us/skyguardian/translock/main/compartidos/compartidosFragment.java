package com.us.skyguardian.translock.main.compartidos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.CompartirCandadoViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class compartidosFragment extends Fragment {

    ArrayList<compartidosVG> listaCandadosCompartidos;
    CompartirCandadoViewModel compartirCandadoViewModel;
    Usuario usuario;
    String url;
    RecyclerView candadosCompartidos;
    LoadingDialog loadingDialog;
    compartidosAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingDialog.isLoading(false);

    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_compartidos_host_fragment);
        View view = inflater.inflate(R.layout.fragment_compartidos, container, false);
        compartirCandadoViewModel = ViewModelProviders.of(getActivity()).get(CompartirCandadoViewModel.class);
        usuario = Usuario.getUser();
        url = getString(R.string.ip)+"skylock/candado/mis_candados_compartidos";
        candadosCompartidos = view.findViewById(R.id.recycler_candados_compartidos);
        candadosCompartidos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaCandadosCompartidos = new ArrayList<compartidosVG>();
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject candado = response.getJSONObject(i);
                                listaCandadosCompartidos.add(new compartidosVG(candado.getString("alias"), candado.getInt("cantidad"), candado.getInt("id"), candado.getString("imei"), candado.getString("identificador")));

                            }

                            adapter = new compartidosAdapter(listaCandadosCompartidos);

                            candadosCompartidos.setAdapter(adapter);
                            loadingDialog.isLoading(false);
                        } catch (Exception e) {
                            loadingDialog.isLoading(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.isLoading(false);
                    }
                }
        );
        loadingDialog.isLoading(true);
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        compartirCandadoViewModel.setIdCandado(null);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_compartidos);
        Menu menu = toolbar.getMenu();
        SearchView search = (SearchView) menu.getItem(0).getActionView();
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView text = search.findViewById(id);
        text.setTextColor(Color.WHITE);
        text.setHintTextColor(Color.WHITE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}