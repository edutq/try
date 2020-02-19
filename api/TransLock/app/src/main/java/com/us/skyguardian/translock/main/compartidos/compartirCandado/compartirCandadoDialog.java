package com.us.skyguardian.translock.main.compartidos.compartirCandado;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.main.compartidos.compartidosAdapter;
import com.us.skyguardian.translock.main.compartidos.compartidosVG;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class compartirCandadoDialog extends DialogFragment {

    ArrayList<candadoSearchVG> listaCandados;
    RecyclerView candados;
    candadoSearchAdapter adapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buscar_candados, null);

        candados = view.findViewById(R.id.recylcer_dialog_candado);
        candados.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCandados = new ArrayList<candadoSearchVG>();

        String url = getString(R.string.ip)+"skylock/candado/mis_candados_propios";

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
                                listaCandados.add(new candadoSearchVG(candado.getInt("id"), candado.getString("alias"), R.drawable.lock, candado.getString("identificador")));

                            }

                            adapter = new candadoSearchAdapter(listaCandados);
                            candados.setAdapter(adapter);
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
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        SearchView search = view.findViewById(R.id.search_candado);
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
        return builder.create();
    }




}
