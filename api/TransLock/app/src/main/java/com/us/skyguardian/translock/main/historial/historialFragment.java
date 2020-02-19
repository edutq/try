package com.us.skyguardian.translock.main.historial;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.candados.CandadosAdapter;
import com.us.skyguardian.translock.main.candados.CandadosVG;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


public class historialFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    ArrayList<historialVG> lista_historial;
    RecyclerView historial;
    LoadingDialog loadingDialog;
    historialAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String fechaInicio;
    String fechaFin;
    public historialFragment() {
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
        View view  = inflater.inflate(R.layout.fragment_historial, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_historial);
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_historial_host_fragment);

        historial = view.findViewById(R.id.historial);
        historial.setLayoutManager(new LinearLayoutManager(getContext()));

        lista_historial = new ArrayList<historialVG>();
        getHistorial();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHistorial();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void getHistorial() {
        lista_historial.clear();
        String url = getString(R.string.ip)+"skylock/historial/mi_historial";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject historia = response.getJSONObject(i);

                                lista_historial.add(new historialVG(historia.getString("alias"),
                                        historia.getString("hora"),
                                        historia.getString("fecha"),
                                        historia.getString("nombre") + " " + historia.getString("apellido"),
                                        historia.getString("evento"),
                                        historia.getString("afectado")));

                            }
                            adapter = new historialAdapter(lista_historial);
                            historial.setAdapter(adapter);
                            loadingDialog.isLoading(false);
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            loadingDialog.isLoading(false);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.isLoading(false);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        loadingDialog.isLoading(true);
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    public void getHistorialPorFecha(String fechaInicio, String fechaFin) {
        lista_historial.clear();
        String url = getString(R.string.ip)+"skylock/historial/mi_historial_por_fecha/"+fechaInicio+"/"+fechaFin;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject historia = response.getJSONObject(i);

                                lista_historial.add(new historialVG(historia.getString("alias"),
                                        historia.getString("hora"),
                                        historia.getString("fecha"),
                                        historia.getString("nombre") + " " + historia.getString("apellido"),
                                        historia.getString("evento"),
                                        historia.getString("afectado")));

                            }
                            adapter = new historialAdapter(lista_historial);
                            historial.setAdapter(adapter);
                            loadingDialog.isLoading(false);
                            //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            loadingDialog.isLoading(false);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.isLoading(false);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        loadingDialog.isLoading(true);
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_historial);
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
        MenuItem calendario = menu.getItem(1);
        calendario.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                datePicker();
                //getHistorialPorFecha("2020-02-13", "2020-02-15");
                return false;
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void datePicker() {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
                        String month;
                        String day;
                        if (monthOfYear + 1 < 10) {
                            month = "0"+(monthOfYear+1);
                        } else {
                            month = Integer.toString(monthOfYear+1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0"+(dayOfMonth);
                        } else {
                            day = dayOfMonth+"";
                        }
                        fechaFin = year+"-"+month+"-"+day;
                        getHistorialPorFecha(fechaInicio, fechaFin);

                    }
                }, year, mes, dia);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
                        String month;
                        String day;
                        if (monthOfYear + 1 < 10) {
                            month = "0"+(monthOfYear+1);
                        } else {
                            month = Integer.toString(monthOfYear+1);
                        }
                        if (dayOfMonth < 10) {
                            day = "0"+(dayOfMonth);
                        } else {
                            day = dayOfMonth+"";
                        }
                        datePickerDialog2.show();
                        fechaInicio = year+"-"+month+"-"+day;

                    }
                }, year, mes, dia);


        datePickerDialog.show();
    }



}
