package com.us.skyguardian.translock.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.PersistentCookieStore;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.main.MainActivity;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    AppBarConfiguration appBarConfiguration;
    String url;
    loginDialog errorDialog;
    loginDialog smsDialog;
    LoadingDialog loadingDialog;
    Usuario usuario;
    SharedPreferences sharedPreferences;
    private static final String PREFERENCES = "USERPREFERENCES";

    enum Codigo {
        RECUPERAR, ACTIVAR, NONE;
    }
    Codigo c;
    Toolbar myToolbar;

    AppCompatActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        errorDialog = new loginDialog();

        smsDialog = new loginDialog();

        myToolbar =  findViewById(R.id.toolbar_login);

        loadingDialog = new LoadingDialog(this, R.id.nav_fragment_login);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        c = Codigo.NONE;
        NavigationView navView = new NavigationView(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_fragment_login);
        appBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph())
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {

                if(destination.getId() != R.id.loginFragment) {

                    myToolbar.setNavigationIcon(R.drawable.back_button_icon);

                }
                if (destination.getId() == R.id.recuperarPasswordFragment) {
                    controller.popBackStack(R.id.recuperarPasswordFragment, false);
                }
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void goRegistrar(View view) {

        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrarFragment);

    }

    public void validarTelefono (final View view) {

        view.setClickable(false);
        final View fragment = view.getRootView();

        EditText telefono = fragment.findViewById(R.id.numero_telefonico_recuperar);
        setEditTextColors(telefono, R.color.textoSecundario);
        String telefonoString = telefono.getText().toString();

        String mensaje = "";
        boolean flag = true;

        if (telefonoString.isEmpty()) {

            setEditTextColors(telefono, R.color.rojo);
            mensaje += "\n"+getString(R.string.error_texto_telefono)+"\n";
            flag = false;
        } else if (!Pattern.matches("^\\d{10}$", telefonoString)) {

            setEditTextColors(telefono, R.color.rojo);
            mensaje += "\n"+getString(R.string.error_texto_telefono_digitos)+"\n";
            flag = false;
        }
        if (flag) {

            url = getString(R.string.ip)+"skylock/sesion/validar/"+telefonoString;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            view.setClickable(true);
                            try {
                                if (response.has("telefono")) {
                                    usuario = Usuario.getUser(response.getInt("id"), response.getString("telefono"),
                                            response.getString("nombre"), response.getString("apellido"),
                                            (response.getInt("activo") == 1), response.getInt("rol"));
                                    c = Codigo.RECUPERAR;
                                    mandarCodigo(fragment);
                                    Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_numeroFragment_to_codigoFragment);
                                } else {
                                        errorDialog.setTitulo("Error");
                                        errorDialog.setMensaje("El número que ingresó no está registrado en el sistema.");
                                        errorDialog.show(getSupportFragmentManager(), "Error");
                                }

                            } catch (Exception e) {

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            view.setClickable(true);
                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");

                        }
                    }
            );
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);

        } else {

            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
            errorDialog.show(getSupportFragmentManager(), "Error");

        }

    }

    public void goRecuperarCodigo(View view) {


        c = Codigo.RECUPERAR;
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_numeroFragment);

    }

    public void setEditTextColors(EditText editText, int color) {
        editText.setTextColor(getResources().getColor(color));
        editText.setHintTextColor(getResources().getColor(color));
    }

    public void verificarCodigo (final View view) {
        view.setClickable(false);
        final View fragment = view.getRootView();
        EditText codigo = fragment.findViewById(R.id.codigo_recuperar);
        String codigoString = codigo.getText().toString();

        if (usuario != null) {

            if( c == Codigo.ACTIVAR) {
                url = getString(R.string.ip)+"skylock/sms/confirmar/activar/+52"+usuario.getTelefono()+"/"+codigoString;

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                view.setClickable(true);
                                if (response.equals("código aceptado")) {

                                    Intent intent = new Intent(fragment.getContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

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
                                view.setClickable(true);
                                errorDialog.setTitulo("Error");
                                errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                        "Por favor intente más tarde o revise su conexión a internet.");
                                errorDialog.show(getSupportFragmentManager(), "Error");

                            }
                        }
                );
                SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
            } else if (c == Codigo.RECUPERAR) {

                url = getString(R.string.ip)+"skylock/sms/confirmar/recuperar/+52"+usuario.getTelefono()+"/"+codigoString;

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                view.setClickable(true);
                                if (response.equals("código aceptado")) {

                                    Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_codigoFragment_to_recuperarPasswordFragment);

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
                                view.setClickable(true);
                                errorDialog.setTitulo("Error");
                                errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                        "Por favor intente más tarde o revise su conexión a internet.");
                                errorDialog.show(getSupportFragmentManager(), "Error");

                            }
                        }
                );
                SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
            }



        } else {
            Navigation.findNavController(view).popBackStack(R.id.loginFragment, false);
        }


    }



    public void mandarCodigo(final View view) {
        view.setClickable(false);
        if (c == Codigo.ACTIVAR) {
            url = getString(R.string.ip)+"skylock/sms/solicitar/activar/+52"+usuario.getTelefono();

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            view.setClickable(true);
                            smsDialog.setTitulo("Activación de Cuenta");
                            smsDialog.setMensaje("Es necesario que active su cuenta antes de iniciar sesión\nSe ha enviado un SMS a "+usuario.getTelefono()+" para activar su cuenta");
                            smsDialog.show(getSupportFragmentManager(), "Error");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            view.setClickable(true);
                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");

                        }
                    }
            );
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
        } else if (c == Codigo.RECUPERAR){
            url = getString(R.string.ip)+"skylock/sms/solicitar/recuperar/+52"+usuario.getTelefono();

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            smsDialog.setTitulo("Activación de Cuenta");
                            smsDialog.setMensaje("Se ha enviado un SMS a "+usuario.getTelefono()+" para recuperar su contraseña");
                            smsDialog.show(getSupportFragmentManager(), "Error");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");

                        }
                    }
            );
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
        }

    }

    public void registrarse(final View view) {
        view.setClickable(false);
        final View fragment = view.getRootView();
        EditText telefono = fragment.findViewById(R.id.numero_telefonico_registrar);
        EditText nombre = fragment.findViewById(R.id.nombre_registrar);
        EditText apellido = fragment.findViewById(R.id.apellido_registrar);
        EditText password = fragment.findViewById(R.id.password_registrar);
        EditText confirmarPassword = fragment.findViewById(R.id.confirmar_password_registrar);

        setEditTextColors(telefono, R.color.textoSecundario);
        setEditTextColors(nombre, R.color.textoSecundario);
        setEditTextColors(apellido, R.color.textoSecundario);
        setEditTextColors(password, R.color.textoSecundario);
        setEditTextColors(confirmarPassword, R.color.textoSecundario);

        String telefonoString = telefono.getText().toString();
        String nombreString = nombre.getText().toString();
        String apellidoString = apellido.getText().toString();
        String passwordString = password.getText().toString();
        String confirmarPasswordString = confirmarPassword.getText().toString();

        String mensaje = "";
        boolean flag = true;

        if (telefonoString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_telefono)+"\n";
            setEditTextColors(telefono, R.color.rojo);
            flag = false;
        } else if (!Pattern.matches("^\\d{10}$", telefonoString)) {

            mensaje += "\n"+getString(R.string.error_texto_telefono_digitos)+"\n";
            setEditTextColors(telefono, R.color.rojo);
            flag = false;
        }
        if (nombreString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_nombre)+"\n";
            setEditTextColors(nombre, R.color.rojo);;
            flag = false;
        } else if (nombreString.length() > 30) {

            mensaje += "\n"+getString(R.string.error_texto_nombre_caracteres)+"\n";
            setEditTextColors(nombre, R.color.rojo);
            flag = false;
        }
        if (apellidoString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_apellido)+"\n";
            setEditTextColors(apellido, R.color.rojo);
            flag = false;
        } else if (apellidoString.length() > 30) {

            mensaje += "\n"+getString(R.string.error_texto_apellido_caracteres)+"\n";
            setEditTextColors(apellido, R.color.rojo);
            flag = false;
        }
        if (passwordString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_password)+"\n";
            setEditTextColors(password, R.color.rojo);
            flag = false;
        }
        if (confirmarPasswordString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_confirmar_password)+"\n";
            setEditTextColors(confirmarPassword, R.color.rojo);
            flag = false;
        }
        if (!passwordString.isEmpty() && !confirmarPasswordString.isEmpty()) {
            if(!passwordString.equals(confirmarPasswordString)) {

                mensaje += "\n"+getString(R.string.error_texto_coincidir_password)+"\n";
                setEditTextColors(password, R.color.rojo);
                setEditTextColors(confirmarPassword, R.color.rojo);
                flag = false;
            }
        }

        if (flag) {

            url = getString(R.string.ip)+"skylock/sesion/registrar/"+nombreString+"/"+apellidoString+"/"+telefonoString+"/"+passwordString;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            view.setClickable(true);
                            try {
                                if (response.has("id")) {
                                    usuario = Usuario.getUser (response.getInt("id"), response.getString("telefono"),
                                            response.getString("nombre"), response.getString("apellido"),
                                            (response.getInt("activo") == 1), response.getInt("rol"));

                                    if (!usuario.isActivo() ) {


                                        c = Codigo.ACTIVAR;
                                        mandarCodigo(fragment);
                                        Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_registrarFragment_to_codigoFragment);
                                    }
                                } else if (response.has("message")) {
                                    errorDialog.setMensaje(response.getString("message"));
                                    errorDialog.show(getSupportFragmentManager(), "Error");
                                }

                            } catch (Exception e) {
                                Toast.makeText(fragment.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            view.setClickable(true);

                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");
                        }
                    }
            );
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);


        } else {
            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
            errorDialog.show(getSupportFragmentManager(), "Error");
        }

    }

    public void move(View view) {

        NavController nav = Navigation.findNavController(view);

        if (c.equals(Codigo.ACTIVAR)) {
            iniciarSesion(view);
        } else if (c.equals(Codigo.RECUPERAR)) {
             nav.navigate(R.id.action_codigoFragment_to_recuperarPasswordFragment);
        }

        c = Codigo.NONE;
        //nav.popBackStack(R.id.codigoFragment, false);

    }

    public void iniciarSesion(View view) {

        final View fragment = view.getRootView();
        EditText telefono = fragment.findViewById(R.id.numero_telefonico);
        EditText password = fragment.findViewById(R.id.password);

        setEditTextColors(telefono, R.color.textoSecundario);
        setEditTextColors(password, R.color.textoSecundario);

        String telefonoString = telefono.getText().toString();
        final String passwordString = password.getText().toString();
        String mensaje = "";



        boolean flag = true;
        if (telefonoString.isEmpty()) {

            setEditTextColors(telefono, R.color.rojo);
            mensaje += "\n"+getString(R.string.error_texto_telefono)+"\n";
            flag = false;
        } else if (!Pattern.matches("^\\d{10}$", telefonoString)) {

            setEditTextColors(telefono, R.color.rojo);
            mensaje += "\n"+getString(R.string.error_texto_telefono_digitos)+"\n";
            flag = false;
        }
        if (passwordString.isEmpty()) {

            setEditTextColors(password, R.color.rojo);
            mensaje += "\n"+getString(R.string.error_texto_password)+"\n";
            flag = false;
        }

        if (flag) {

            url = getString(R.string.ip)+"skylock/sesion/login/"+telefonoString+"/"+passwordString;

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            loadingDialog.isLoading(false);
                            try {
                                if (response.has("id")) {
                                    usuario = Usuario.getUser (response.getInt("id"), response.getString("telefono"),
                                            response.getString("nombre"), response.getString("apellido"),
                                            (response.getInt("activo") == 1), response.getInt("rol"));

                                    sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("telefono", usuario.getTelefono())
                                            .putString("password", passwordString).commit();

                                    if (!usuario.isActivo()) {
                                        c = Codigo.ACTIVAR;
                                        mandarCodigo(fragment);
                                        Navigation.findNavController(getCurrentFocus()).navigate(R.id.action_loginFragment_to_codigoFragment);
                                    } else {
                                        Intent intent = new Intent(fragment.getContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else if (response.has("message")) {
                                    errorDialog.setMensaje(response.getString("message"));
                                    errorDialog.show(getSupportFragmentManager(), "Error");
                                }
                            } catch (Exception e) {
                                Toast.makeText(fragment.getContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.isLoading(false);
                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");
                        }
                    }
            );
            loadingDialog.isLoading(true);
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);

        } else {
            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
            errorDialog.show(getSupportFragmentManager(), "Error");
        }



    }

    public void recuperarPassword(final View view) {

        view.setClickable(false);
        final View fragment = view.getRootView();
        EditText password = fragment.findViewById(R.id.nueva_password);
        EditText confirmarPassword = fragment.findViewById(R.id.confirmar_nueva_password);
        setEditTextColors(confirmarPassword, R.color.textoSecundario);
        setEditTextColors(confirmarPassword, R.color.textoSecundario);
        String passwordString = password.getText().toString();
        String confirmarPasswordString = confirmarPassword.getText().toString();

        String mensaje = "";
        boolean flag = true;

        if (passwordString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_password)+"\n";
            setEditTextColors(password, R.color.rojo);
            flag = false;
        }
        if (confirmarPasswordString.isEmpty()) {

            mensaje += "\n"+getString(R.string.error_texto_confirmar_password)+"\n";
            setEditTextColors(confirmarPassword, R.color.rojo);
            flag = false;
        }
        if (!passwordString.isEmpty() && !confirmarPasswordString.isEmpty()) {
            if(!passwordString.equals(confirmarPasswordString)) {

                mensaje += "\n"+getString(R.string.error_texto_coincidir_password)+"\n";
                setEditTextColors(password, R.color.rojo);
                setEditTextColors(confirmarPassword, R.color.rojo);
                flag = false;
            }
        }

        if (flag) {


            url = getString(R.string.ip)+"skylock/sesion/recuperarpass/"+usuario.getTelefono()+"/"+passwordString;
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            view.setClickable(true);
                            if (response.equals("Contraseña modificada!")) {

                                Navigation.findNavController(getCurrentFocus()).popBackStack(R.id.loginFragment, false);

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
                            view.setClickable(true);
                            errorDialog.setTitulo("Error");
                            errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                    "Por favor intente más tarde o revise su conexión a internet.");
                            errorDialog.show(getSupportFragmentManager(), "Error");
                        }
                    }
            );
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(request);
        } else {
            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
            errorDialog.show(getSupportFragmentManager(), "Error");
        }

        //Navigation.findNavController(view).navigate(R.id.action_recuperarPasswordFragment_to_loginFragment);

    }
}
