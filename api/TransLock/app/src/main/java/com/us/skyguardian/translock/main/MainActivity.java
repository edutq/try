package com.us.skyguardian.translock.main;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.us.skyguardian.translock.DB.AppDatabase;
import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Candado.CandadoDao;
import com.us.skyguardian.translock.DB.Historial.Historial;
import com.us.skyguardian.translock.DB.Historial.HistorialDao;
import com.us.skyguardian.translock.DB.User.User;
import com.us.skyguardian.translock.DB.User.UserDao;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.LoginActivity;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.candados.CandadosViewModel;
import com.us.skyguardian.translock.main.candados.candadoView.CandadoViewViewModel;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.compartirCandadoDialog;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.CompartirCandadoViewModel;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.UsuarioViewModel;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.horario.HorarioViewModel;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.usuarioHorarioAdapter;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.usuarioHorarioVG;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuariosCompartidosDialog;
import com.us.skyguardian.translock.offline.OfflineActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {



    compartirCandadoDialog dialogcandado;
    usuariosCompartidosDialog dialogusuarios;
    mainPager viewPager;
    ArrayList<BaseFragment> fragments;
    Stack<Integer> backstack;
    BottomNavigationView navView;
    CompartirCandadoViewModel compartirCandadoViewModel;
    CandadoViewViewModel candadoViewViewModel;
    UsuarioViewModel usuarioViewModel;
    HorarioViewModel horarioViewModel;
    String url;
    Usuario usuario;
    SharedPreferences sharedPreferences;
    loginDialog errorDialog;
    CookieStore cookieStore;
    CookieManager manager;
    LoadingDialog loadingDialogCompartidos;
    LoadingDialog loadingDialogConfiguracion;
    AppDatabase appDatabase;
    Handler handler;
    CandadosViewModel candadosViewModel;

    //CookieHandler handler;
    private static final String PREFERENCES = "USERPREFERENCES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        //SingletonRequestQueue.getInstance(getApplicationContext());
        errorDialog = new loginDialog();

        manager = new CookieManager();
        CookieHandler.setDefault(manager);



        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if (isNetworkAvailable()) {
            appDatabase = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "translock").build();
            if (sharedPreferences.contains("telefono")) {
                String telefono = sharedPreferences.getString("telefono", "1234");
                String password = sharedPreferences.getString("password", "password");
                url = getString(R.string.ip)+"skylock/sesion/login/"+telefono+"/"+password;
                //Toast.makeText(getBaseContext(), telefono+"///"+password, Toast.LENGTH_LONG).show();

                JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_LONG).show();
                            try {
                                if (response.has("id")) {

                                    usuario = Usuario.getUser (response.getInt("id"), response.getString("telefono"),
                                            response.getString("nombre"), response.getString("apellido"),
                                            (response.getInt("activo") == 1), response.getInt("rol"));
                                    setContentView(R.layout.activity_main);


                                    finishLoad();
                                } else {
                                    goLogin();
                                }
                            } catch (Exception e) {
                                goLogin();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            goOffline();
                        }
                    }
                );

                SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);

            } else {
                goLogin();
            }
        } else {
            if (sharedPreferences.contains("telefono")) {
                goOffline();
            } else {
                goLogin();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static class InsertUser extends AsyncTask<User, Void, Void> {

        UserDao userDao;

        public InsertUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.cleanInsertTransaction(users[0]);

            return null;
        }
    }

    private static class SetHistorial extends AsyncTask<Void, Void, Void> {

        HistorialDao historialDao;
        Activity activity;

        public SetHistorial(HistorialDao historialDao, Activity activity) {
            this.historialDao = historialDao;
            this.activity = activity;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            List<Historial> listaHistorial = historialDao.getCleanTransaction();
            for (Historial historial : listaHistorial) {
                String url = activity.getString(R.string.ip) + "skylock/historial/registrar_offline/" +
                        historial.fecha + "/" +
                        historial.idUsuario + "/" +
                        historial.idCandado + "/" +
                        historial.idEvento;

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {



                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("BLE", error.getMessage() + "aqui");
                            }
                        }
                );
                SingletonRequestQueue.getInstance(activity.getApplicationContext()).addToRequestQueue(request);
            }
//            Toast.makeText(activity, "EVENTOS REGISTRADOS", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    public void finishLoad() {


        checkBtPermissions();
        final User user = new User();
        user.id = usuario.getId();
        user.telefono = usuario.getTelefono();
        user.apellido = usuario.getApellido();
        user.nombre = usuario.getNombre();

        new InsertUser(appDatabase.userDao()).execute(user);
        new SetHistorial(appDatabase.historialDao(), this).execute();
        loadingDialogCompartidos = new LoadingDialog(this, R.id.nav_compartidos_host_fragment);
        loadingDialogConfiguracion = new LoadingDialog(this, R.id.nav_configuracion_host_fragment);
        compartirCandadoViewModel = ViewModelProviders.of(this).get(CompartirCandadoViewModel.class);
        candadoViewViewModel = ViewModelProviders.of(this).get(CandadoViewViewModel.class);
        candadosViewModel = ViewModelProviders.of(this).get(CandadosViewModel.class);
        horarioViewModel = ViewModelProviders.of(this).get(HorarioViewModel.class);
        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);

        candadosViewModel.setAppDatabase(appDatabase);

        fragments = new ArrayList<>();

        fragments.add(new BaseFragment(R.layout.layout_candado, R.id.toolbar_candado, R.id.nav_candado_host_fragment));
        fragments.add(new BaseFragment(R.layout.layout_compartidos, R.id.toolbar_compartidos, R.id.nav_compartidos_host_fragment));
        fragments.add(new BaseFragment(R.layout.layout_historial, R.id.toolbar_historial, R.id.nav_historial_host_fragment));
        fragments.add(new BaseFragment(R.layout.layout_configuracion, R.id.toolbar_configuracion, R.id.nav_configuracion_host_fragment));


        backstack = new Stack<Integer>();
        backstack.push(0);


        viewPager = findViewById(R.id.nav_viewPager);
        viewPager.setOffscreenPageLimit(3);
        mainpagerAdapter adapter = new mainpagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);


        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.navigation_candados:

                        if (viewPager.getCurrentItem() != 0) {

                            setPagerNavigation(0, menuItem);
                        }
                        break;

                    case R.id.navigation_compartidos:

                        if (viewPager.getCurrentItem() != 1) {

                            setPagerNavigation(1, menuItem);
                        }
                        break;

                    case R.id.navigation_historial:

                        if (viewPager.getCurrentItem() != 2) {

                            setPagerNavigation(2, menuItem);
                        }
                        break;

                    case R.id.navigation_configuracion:
                        if (viewPager.getCurrentItem() != 3) {

                            setPagerNavigation(3, menuItem);
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void goLogin () {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goOffline () {
        Intent intent = new Intent (this, OfflineActivity.class);
        startActivity(intent);
        finish();
    }

    public void setPagerNavigation (int position, MenuItem menuItem) {

        //viewPager.removeAllViews();
        viewPager.setCurrentItem(position);
        menuItem.setChecked(true);
        backstack.remove(new Integer(position));
        backstack.push(new Integer(position));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.barra_candados, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        BaseFragment fragment = fragments.get(viewPager.getCurrentItem());
        NavController navController = fragment.getNavController();
        AppBarConfiguration appBarConfig = fragment.getAppBarConfiguration();
        return NavigationUI.navigateUp(navController, appBarConfig)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {


        BaseFragment fragment = fragments.get(viewPager.getCurrentItem());
        Boolean navigationUp = fragment.onBackPress();

        if(!navigationUp) {

            if (!navigateBack(viewPager.getCurrentItem())) {
                super.onBackPressed();
                finish();
            }
        }

    }

    public boolean navigateBack (int position) {

        Log.i("BLE", backstack.size()+"");
        if (backstack.size() > 1) {

            backstack.remove(new Integer(position));
            navView.getMenu().getItem(position).setChecked(false);
            viewPager.setCurrentItem(backstack.peek());
            navView.getMenu().getItem(viewPager.getCurrentItem()).setChecked(true);
            return true;
        }
        return false;

    }

    public void verCandado(View view) {

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Ble not supported", Toast.LENGTH_SHORT).show();
            //finish();
        } else {
            TextView nombre = view.findViewById(R.id.nombre_candado);
            TextView imei = view.findViewById(R.id.imei_candado);
            TextView marca = view.findViewById(R.id.marca_candado);
            TextView id = view.findViewById(R.id.id_candado);
            TextView admin = view.findViewById(R.id.administrador_candado);
            TextView identificador = view.findViewById(R.id.identificador_candado);
            candadoViewViewModel.setNombreCandado(nombre.getText().toString());
            candadoViewViewModel.setImei(imei.getText().toString());
            candadoViewViewModel.setMarca(marca.getText().toString());
            candadoViewViewModel.setIdCandado(new Integer(id.getText().toString()));
            candadoViewViewModel.setAdmin((admin.getText().toString()=="1"));
            candadoViewViewModel.setIdentificador(identificador.getText().toString());
            Navigation.findNavController(view).navigate(R.id.action_candadosFragment_to_candadoViewFragment);
        }
    }

    public void checkBtPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.BLUETOOTH, Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    0);
        }
    }

    public void verCompartidos(View view) {

        TextView idCandado = view.findViewById(R.id.id_candado_compartido);
        TextView nombre = view.findViewById(R.id.nombre_candado_compartido);
        compartirCandadoViewModel.setIdCandado(new Integer(idCandado.getText().toString()));
        compartirCandadoViewModel.setNombreCandado(nombre.getText().toString());
        Navigation.findNavController(view).navigate(R.id.action_compartidosFragment_to_usuariosCompartidosFragment);

    }

    public void cerrarSesion(View view) {

        final View fragment = view.getRootView();
        url = getString(R.string.ip)+"skylock/sesion/cerrar";
        final Intent intent = new Intent(fragment.getContext(), LoginActivity.class);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingDialogConfiguracion.isLoading(false);
                Toast.makeText(fragment.getContext(), response, Toast.LENGTH_LONG).show();
                sharedPreferences.edit().clear().commit();
                Usuario.destroyUser();
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialogConfiguracion.isLoading(false);
                Toast t = Toast.makeText(fragment.getContext(), error.toString(), Toast.LENGTH_LONG);
                t.show();
                sharedPreferences.edit().clear().commit();
                Usuario.destroyUser();
                startActivity(intent);
                finish();
            }
        });
        loadingDialogConfiguracion.isLoading(true);
        SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);

    }

    public void verUsuario(View view) {

        TextView id = view.findViewById(R.id.id_usuario);
        TextView nombre = view.findViewById(R.id.nombre_usuario);
        TextView admin = view.findViewById(R.id.tipo_usuario);
        TextView telefono = view.findViewById(R.id.telefono_usuario);
        usuarioViewModel.setNombreUsuario(nombre.getText().toString());
        usuarioViewModel.setIdUsuario(new Integer(id.getText().toString()));
        usuarioViewModel.setPermisoAdministrador((admin.getText().toString().equals("Administrador")));
        usuarioViewModel.setTelefono(telefono.getText().toString());

        Navigation.findNavController(view).navigate(R.id.action_usuariosCompartidosFragment_to_usuarioFragment);

    }

    public void agregarPermiso(View view) {

        Navigation.findNavController(view).navigate(R.id.action_usuarioFragment_to_horarioFragment);

    }

    public void configHorario(View view) {

        final View layout = (View) view.getParent().getParent();
        TextView id = layout.findViewById(R.id.id_permiso_usuario_candado);
        TextView fecha_inicio = layout.findViewById(R.id.fecha_inicio_permiso_usuario);
        TextView hora_inicio = layout.findViewById(R.id.hora_inicio_permiso_usuario);
        TextView fecha_fin = layout.findViewById(R.id.fecha_fin_permiso_usuario);
        TextView hora_fin = layout.findViewById(R.id.hora_fin_permiso_usuario);
        horarioViewModel.setId(new Integer(id.getText().toString()));
        horarioViewModel.setFecha_inicio(fecha_inicio.getText().toString());
        horarioViewModel.setFecha_fin(fecha_fin.getText().toString());
        horarioViewModel.setHora_inicio(hora_inicio.getText().toString());
        horarioViewModel.setHora_fin(hora_fin.getText().toString());
        Navigation.findNavController(view).navigate(R.id.action_usuarioFragment_to_horarioFragment);

    }

    public void eliminarPermiso(final View view) {

        //Toast.makeText(this, layout.getParent().getParent().toString(), Toast.LENGTH_SHORT).show();

        androidx.appcompat.app.AlertDialog aviso;
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);

        builder.setTitle("Aviso!").setMessage("¿Seguro que desea eliminar este permiso?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final View layout = (View) view.getParent().getParent();
                TextView id = layout.findViewById(R.id.id_permiso_usuario_candado);
                String url;
                url = getString(R.string.ip)+"skylock/candado_usuario/eliminar_permiso/"+id.getText().toString();
                StringRequest request = new StringRequest(
                        Request.Method.DELETE,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                loadingDialogCompartidos.isLoading(false);
                                if (response.equals("Exito")) {

                                    Toast.makeText(MainActivity.this, "Permiso eliminado", Toast.LENGTH_SHORT).show();
                                    RecyclerView permisos = (RecyclerView)  layout.getParent().getParent();
                                    usuarioHorarioAdapter adapter = (usuarioHorarioAdapter) permisos.getAdapter();
                                    ArrayList<usuarioHorarioVG> listahorarios = adapter.getHorarios();
                                    listahorarios.remove(0);

                                } else {
                                    errorDialog.setTitulo("Error");
                                    errorDialog.setMensaje(response);
                                    errorDialog.show(getSupportFragmentManager(), "Error");
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loadingDialogCompartidos.isLoading(false);
                                errorDialog.setTitulo("Error");
                                errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                        "Por favor intente más tarde o revise su conexión a internet.");
                                errorDialog.show(getSupportFragmentManager(), "Error");
                            }
                        }
                );
                loadingDialogCompartidos.isLoading(true);
                SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
            }
        });

        aviso = builder.create();
        aviso.show();

    }

    public void cambiarPassword(View view) {

        Navigation.findNavController(view).navigate(R.id.action_configuracionFragment_to_cambiarPasswordFragment);

    }

    public void cambiarNombre(View view) {

        Navigation.findNavController(view).navigate(R.id.action_configuracionFragment_to_cambiarNombreFragment);

    }

    public void compartirCandado(View view) {

        Navigation.findNavController(view).navigate(R.id.action_compartidosFragment_to_compartirCandadoFragment);

    }

    public void buscarCandado(View view) {

        //new SimpleSearchDialogCompat<>(MainActivity.this, "Candado...", )

        dialogcandado = new compartirCandadoDialog();
        dialogcandado.show(getSupportFragmentManager(), "Seleccionar Candado");
        //Navigation.findNavController(view).navigate(R.id.action_compartirCandadoFragment_to_buscarCandadoFragment);
    }

    public void buscarUsuario (View view) {

        dialogusuarios = new usuariosCompartidosDialog();
        dialogusuarios.show(getSupportFragmentManager(), "Seleccionar Usuario");
        //Navigation.findNavController(view).navigate(R.id.action_compartirCandadoFragment_to_buscarUsuarioFragment);

    }

    public void cambiarNombreCandado (View view) {

        Navigation.findNavController(view).navigate(R.id.action_candadoViewFragment_to_cambiarNombreCandadoFragment);
    }

    public void agregarUsuarioCandado (View view) {

        Navigation.findNavController(view).navigate(R.id.action_usuariosCompartidosFragment_to_compartirCandadoFragment);

    }

    public void calendario(View view) {

        TextView date = new TextView(this);
        TextView time = new TextView(this);

        if (view.getId() == R.id.panel_de) {

            date = view.findViewById(R.id.fecha_de_horario);
            time = view.findViewById(R.id.hora_de_horario);

        } else if (view.getId() == R.id.panel_a) {

            date = view.findViewById(R.id.fecha_a_horario);
            time = view.findViewById(R.id.hora_a_horario);

        }

        datePicker(date, time);

    }

    public void datePicker(final TextView date, final TextView time) {
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
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
                        date.setText(year+"-"+month+"-"+day);
                        timePicker(time);

                    }
                }, year, mes, dia);

        datePickerDialog.show();
    }

    public void timePicker(final TextView time) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour;
                String min;
                if (hourOfDay < 10) {
                    hour = "0"+hourOfDay;
                } else {
                    hour = hourOfDay+"";
                }
                if (minute < 10) {
                    min = "0"+(minute);
                } else {
                    min = minute+"";
                }
                time.setText(hour+":"+min);

            }
        }, hora, minute, true);

        timePickerDialog.show();
    }

    public void seleccionarCandadoSearch(View view) {

        CircleImageView foto = view.findViewById(R.id.foto_dialog_candado);
        TextView nombre = view.findViewById(R.id.nombre_dialog_candado);
        TextView id = view.findViewById(R.id.id_dialog_candado);

        CircleImageView foto_seleccionado = this.findViewById(R.id.foto_candado_seleccionado);
        TextView nombre_seleccionado = this.findViewById(R.id.nombre_candado_seleccionado);
        TextView id_seleccionado = this.findViewById(R.id.id_candado_seleccionado);
        LinearLayoutCompat layout_seleccionado = this.findViewById(R.id.candado_seleccionado);

        foto_seleccionado.setImageDrawable(foto.getDrawable());
        nombre_seleccionado.setText(nombre.getText());
        id_seleccionado.setText(id.getText());
        layout_seleccionado.setVisibility(View.VISIBLE);

        dialogcandado.dismiss();

    }

    public void seleccionarUsuarioSearch(View view) {


        CircleImageView foto = view.findViewById(R.id.foto_usuario_search);
        TextView nombre = view.findViewById(R.id.nombre_usuario_search);
        TextView telefono = view.findViewById(R.id.numero_telefono_search);
        TextView id = view.findViewById(R.id.id_usuario_search);

        CircleImageView foto_seleccionado = this.findViewById(R.id.foto_usuario_seleccionado);
        TextView nombre_seleccionado = this.findViewById(R.id.nombre_usuario_seleccionado);
        TextView telefono_seleccionado = this.findViewById(R.id.telefono_usuario_seleccionado);
        TextView id_seleccionado = this.findViewById(R.id.id_usuario_seleccionado);

        LinearLayoutCompat layout_seleccionado = this.findViewById(R.id.usuario_seleccionado);

        nombre_seleccionado.setText(nombre.getText());
        telefono_seleccionado.setText(telefono.getText());
        foto_seleccionado.setImageDrawable(foto.getDrawable());
        id_seleccionado.setText(id.getText());
        layout_seleccionado.setVisibility(View.VISIBLE);

        dialogusuarios.dismiss();


    }


}
