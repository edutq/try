package com.us.skyguardian.translock.main.configuracion.cambiar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.loginDialog;


public class cambiarPasswordFragment extends Fragment {

    EditText actual;
    EditText nueva;
    EditText confirmar;
    Button guardar;
    loginDialog errorDialog;
    LoadingDialog loadingDialog;
    Usuario usuario;

    public cambiarPasswordFragment() {
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
        View view = inflater.inflate(R.layout.fragment_cambiar_password, container, false);

        actual = view.findViewById(R.id.password_actual);
        nueva = view.findViewById(R.id.nueva_password_cambiar);
        confirmar = view.findViewById(R.id.confirmar_nueva_password_cambiar);
        guardar = view.findViewById(R.id.guardar_cambios_password);

        usuario = Usuario.getUser();

        errorDialog = new loginDialog();
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_configuracion_host_fragment);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarPassword();
                Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

    public void cambiarPassword() {

        final String passactual = actual.getText().toString();
        final String passnueva = nueva.getText().toString();
        final String passconfirmar = confirmar.getText().toString();
        String mensaje = "";
        boolean flag = true;

        if (passactual.isEmpty()) {

            mensaje += "\n"+"No haz ingresado la contraseña actual"+"\n";
            flag = false;
        }
        if (passnueva.isEmpty()) {

            mensaje += "\n"+"No haz ingresado la nueva contraseña"+"\n";
            flag = false;
        }
        if (passconfirmar.isEmpty()) {

            mensaje += "\n"+"No haz ingresado la confirmación de contraseña"+"\n";
            flag = false;
        }
        if (!passnueva.isEmpty() && !passconfirmar.isEmpty()) {
            if (!passconfirmar.equals(passnueva)) {

                mensaje += "\n"+"Las contraseñas no coinciden"+"\n";
                flag = false;
            }
        }
        if (flag) {

            String url;
            url = getString(R.string.ip)+"skylock/usuario/cambiarpass/"+passactual+"/"+passnueva;
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            loadingDialog.isLoading(false);
                            if (response.equals("Exito")) {

                                Toast.makeText(getActivity(), "Contraseña modificada!", Toast.LENGTH_SHORT).show();
                                //candadoViewViewModel.setNombreCandado(nuevoNombre);
                                Navigation.findNavController(guardar).popBackStack();

                            } else {
                                errorDialog.setTitulo("Error");
                                errorDialog.setMensaje(response);
                                errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
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
                            errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                        }
                    }
            );
            loadingDialog.isLoading(true);
            SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        } else {
            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
            errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
        }

    }


}
