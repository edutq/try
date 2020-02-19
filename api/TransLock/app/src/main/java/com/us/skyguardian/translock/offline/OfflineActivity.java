package com.us.skyguardian.translock.offline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.us.skyguardian.translock.DB.AppDatabase;
import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Candado.CandadoDao;
import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.DB.Permiso.PermisoDao;
import com.us.skyguardian.translock.DB.User.User;
import com.us.skyguardian.translock.DB.User.UserDao;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.main.MainActivity;
import com.us.skyguardian.translock.main.candados.candadoView.CandadoViewViewModel;

import java.util.ArrayList;
import java.util.List;

public class OfflineActivity extends AppCompatActivity {


    AppBarConfiguration appBarConfiguration;
    AppDatabase appDatabase;
    OfflineViewModel offlineViewModel;
    Toolbar toolbar;
    CandadoViewViewModel candadoViewViewModel;
    SharedPreferences sharedPreferences;

    private static final String PREFERENCES = "USERPREFERENCES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        offlineViewModel = ViewModelProviders.of(this).get(OfflineViewModel.class);
        candadoViewViewModel = ViewModelProviders.of(this).get(CandadoViewViewModel.class);
        NavigationView navView = new NavigationView(this);

        NavController navController = Navigation.findNavController(this, R.id.host_candado_offiline);
        appBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph()).build();

        toolbar = findViewById(R.id.toolbar_candado_offline);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "translock").build();
        offlineViewModel.setAppDatabase(appDatabase);

        try {
            List<Candado> listaCandados = new GetCandados(appDatabase.candadoDao()).execute().get();
            List<Permiso> listPermisos = new GetPermisos(appDatabase.permisoDao()).execute().get();
            User user = new GetUser(appDatabase.userDao(), sharedPreferences.getString("telefono", "1234")).execute().get();
            offlineViewModel.setListaCandadosOffiline((ArrayList<Candado>) listaCandados);
            offlineViewModel.setListaPermisosOffline((ArrayList<Permiso>) listPermisos);
            offlineViewModel.setUserId(new Integer(user.id));
        } catch (Exception e) {


        }
    }

    private static class GetPermisos extends AsyncTask<Void, Void, List<Permiso>> {

        PermisoDao permisoDao;

        public GetPermisos(PermisoDao permisoDao) {
            this.permisoDao = permisoDao;
        }


        @Override
        protected List<Permiso> doInBackground(Void... voids) {
            return permisoDao.getAll();
        }
    }

    private static class GetUser extends AsyncTask<Void, Void, User> {

        UserDao userDao;
        String telefono;
        public GetUser(UserDao userDao, String telefono) {
            this.userDao = userDao;
            this.telefono = telefono;
        }


        @Override
        protected User doInBackground(Void... voids) {

            return userDao.findByPhone(telefono);
        }
    }

    private static class GetCandados extends AsyncTask<Void, Void, List<Candado>> {

        CandadoDao candadoDao;

        public GetCandados(CandadoDao candadoDao) {
            this.candadoDao = candadoDao;
        }


        @Override
        protected List<Candado> doInBackground(Void... voids) {
            return candadoDao.getAll();
        }
    }



    public void verCandadoOffline(View view) {

        TextView nombre = view.findViewById(R.id.nombre_candado_offline);
        TextView imei = view.findViewById(R.id.imei_candado_offline);
        TextView marca = view.findViewById(R.id.marca_candado_offline);
        TextView id = view.findViewById(R.id.id_candado_offline);
        TextView admin = view.findViewById(R.id.administrador_candado_offline);
        TextView identificador = view.findViewById(R.id.identificador_candado_offline);
        candadoViewViewModel.setNombreCandado(nombre.getText().toString());
        candadoViewViewModel.setImei(imei.getText().toString());
        candadoViewViewModel.setMarca(marca.getText().toString());
        candadoViewViewModel.setIdCandado(new Integer(id.getText().toString()));
        candadoViewViewModel.setAdmin((admin.getText().toString()=="1"));
        candadoViewViewModel.setIdentificador(identificador.getText().toString());
        Navigation.findNavController(view).navigate(R.id.action_offlineCandadosFragment_to_offlineCandadoViewFragment);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.host_candado_offiline);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
