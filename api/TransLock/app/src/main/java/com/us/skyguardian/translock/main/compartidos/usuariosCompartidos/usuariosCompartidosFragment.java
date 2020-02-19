package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.CompartirCandadoViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class usuariosCompartidosFragment extends Fragment {

    ArrayList<usuariosCompartidosVG> listausuarios;
    CompartirCandadoViewModel compartirCandadoViewModel;
    Integer idCandado;
    String url;
    RecyclerView usuarios;
    LoadingDialog loadingDialog;
    usuariosCompartidosAdapter adapter;

    public usuariosCompartidosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingDialog.isLoading(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios_compartidos, container, false);

        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_compartidos_host_fragment);

        compartirCandadoViewModel = ViewModelProviders.of(getActivity()).get(CompartirCandadoViewModel.class);
        idCandado = compartirCandadoViewModel.getIdCandado().getValue();

        usuarios = view.findViewById(R.id.usuarios_compartidos);
        usuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        listausuarios = new ArrayList<usuariosCompartidosVG>();

        url = getString(R.string.ip)+"skylock/candado/usuarios_candado_compartido/"+idCandado;
        final JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject usuario = response.getJSONObject(i);

                                listausuarios.add(new usuariosCompartidosVG(usuario.getInt("id"), usuario.getString("telefono"), usuario.getString("nombre")+" "
                                        +usuario.getString("apellido"), 0, (usuario.getInt("administrador")==1)));
                            }

                            adapter = new usuariosCompartidosAdapter(listausuarios);

                            usuarios.setAdapter(adapter);
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
}
