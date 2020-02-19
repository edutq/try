package com.us.skyguardian.translock.main.candados;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Candado.CandadoDao;
import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.DB.Permiso.PermisoDao;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.PersistentCookieStore;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

public class CandadosFragment extends Fragment {

    loginDialog errorDialog;
    ArrayList<CandadosVG> listaCandados;

    SharedPreferences sharedPreferences;
    RecyclerView candados;
    Usuario usuario;
    LoadingDialog loadingDialog;
    CandadosAdapter adapter;
    CandadosViewModel candadosViewModel;
    private static final String PREFERENCES = "USERPREFERENCES";
    int foto_condicion;
    List<Candado> candadosDBList;
    List<Permiso> permisosDBList;
    SwipeRefreshLayout swipeRefreshLayout;
    public CandadosFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loadingDialog.isLoading(false);

    }

    public class InsertLocks extends AsyncTask<List<Candado>, Void, Void> {

        CandadoDao candadoDao;

        public InsertLocks(CandadoDao candadoDao) {
            this.candadoDao = candadoDao;
        }


        @Override
        protected Void doInBackground(List<Candado>... candados) {
            candadoDao.cleanInsertTransaction(candados[0]);
            return null;
        }

    }

    public class InsertPermisos extends AsyncTask<List<Permiso>, Void, Void> {

        PermisoDao permisoDao;

        public InsertPermisos(PermisoDao permisoDao) {
            this.permisoDao = permisoDao;
        }


        @Override
        protected Void doInBackground(List<Permiso>... permisos) {
            permisoDao.cleanInsertTransaction(permisos[0]);
            return null;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        errorDialog = new loginDialog();
        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        View root = inflater.inflate(R.layout.fragment_candados, container, false);

        swipeRefreshLayout = root.findViewById(R.id.refresh_candados);
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_candado_host_fragment);
        candadosDBList = new ArrayList<>();
        permisosDBList = new ArrayList<>();

        candadosViewModel = ViewModelProviders.of(getActivity()).get(CandadosViewModel.class);

        //loadingDialog.show(getActivity().getSupportFragmentManager(), "Loading");

        listaCandados = new ArrayList<CandadosVG>();

        candados = root.findViewById(R.id.recycler_candado);
        candados.setLayoutManager(new LinearLayoutManager(getContext()));
        usuario = Usuario.getUser();


        getCandados();

        getPermisos();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCandados();

                getPermisos();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }

    public void getCandados() {
        candadosDBList.clear();
        listaCandados.clear();
        String url = getString(R.string.ip)+"skylock/candado/mis_candados";
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
                                if (candado.getInt("dueno") == usuario.getId()) {
                                    foto_condicion = R.drawable.candado_mio;
                                } else {
                                    foto_condicion = R.drawable.candado_compartido_por;
                                }
                                listaCandados.add(new CandadosVG(candado.getString("alias"), R.drawable.lock, foto_condicion, candado.getInt("id"), candado.getString("imei"), candado.getString("marca"), (candado.getInt("administrador") == 1), candado.getString("identificador")));
                                Candado candadoDb = new Candado();
                                candadoDb.alias = candado.getString("alias");
                                candadoDb.id = candado.getInt("id");
                                candadoDb.macAddress = candado.getString("imei");
                                candadoDb.marca = candado.getInt("marca");
                                candadoDb.identificadorUnico = candado.getString("identificador");
                                candadoDb.administrador = candado.getInt("administrador");
                                candadosDBList.add(candadoDb);

                            }
                            candadosViewModel.setCandados(candadosDBList);
                            new InsertLocks(candadosViewModel.getAppDatabase().candadoDao()).execute(candadosDBList);
                            adapter = new CandadosAdapter(listaCandados);
                            candados.setAdapter(adapter);
                            loadingDialog.isLoading(false);
                            //loadingDialog.dismiss();
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

    public void getPermisos() {
        permisosDBList.clear();
        String urlPermisos = getString(R.string.ip)+"skylock/candado_usuario/mis_permisos";
        JsonArrayRequest requestPermisos = new JsonArrayRequest(
                Request.Method.GET,
                urlPermisos,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject permiso = response.getJSONObject(i);

                                Permiso permisoDb = new Permiso();
                                permisoDb.id = permiso.getInt("id");
                                permisoDb.idCandado = permiso.getInt("id_candado");
                                permisoDb.fechaInicio = permiso.getString("fecha_inicio");
                                permisoDb.horaInicio = permiso.getString("hora_inicio");
                                permisoDb.fechaFin = permiso.getString("fecha_fin");
                                permisoDb.horaFin = permiso.getString("hora_fin");


                                permisosDBList.add(permisoDb);

                            }
                            candadosViewModel.setCandados(candadosDBList);
                            new InsertPermisos(candadosViewModel.getAppDatabase().permisoDao()).execute(permisosDBList);
                            adapter = new CandadosAdapter(listaCandados);
                            candados.setAdapter(adapter);
                            loadingDialog.isLoading(false);
                            //loadingDialog.dismiss();
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
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(requestPermisos);
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_candado);
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
        Log.i("BLE", menu.getItem(0).toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}