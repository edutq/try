package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.candadoSearchAdapter;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.candadoSearchVG;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class usuariosCompartidosDialog extends DialogFragment {

    ArrayList<usuariosCompartidosSearchVG> listaUsuarios;
    usuariosCompartidosSearchAdapter adapter;
    RecyclerView usuarios;

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buscar_usuarios, null);

        usuarios = view.findViewById(R.id.recylcer_dialog_usuario);
        usuarios.setLayoutManager(new LinearLayoutManager(getContext()));


        listaUsuarios = new ArrayList<usuariosCompartidosSearchVG>();

        String url = getString(R.string.ip)+"skylock/usuario/buscar";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject usuario = response.getJSONObject(i);
                                listaUsuarios.add(new usuariosCompartidosSearchVG(usuario.getInt("id"),
                                                                            usuario.getString("nombre") + " " + usuario.getString("apellido"),
                                                                    usuario.getString("telefono"), R.drawable.user));

                            }

                            adapter = new usuariosCompartidosSearchAdapter(listaUsuarios);
                            usuarios.setAdapter(adapter);
                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

        builder.setView(view)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        SearchView search = view.findViewById(R.id.search_usuario);
        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView text = search.findViewById(id);
        text.setHintTextColor(Color.WHITE);
        text.setTextColor(Color.WHITE);

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


        return builder.create();
    }
}
