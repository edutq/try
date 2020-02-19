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


public class cambiarNombreFragment extends Fragment {

    Button guardar;
    LoadingDialog loadingDialog;
    EditText nombre;
    EditText apellido;
    loginDialog errorDialog;
    Usuario usuario;
    public cambiarNombreFragment() {
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
        View view = inflater.inflate(R.layout.fragment_cambiar_nombre, container, false);

        nombre = view.findViewById(R.id.cambiar_nombre);
        apellido = view.findViewById(R.id.cambiar_apellido);
        errorDialog = new loginDialog();
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_configuracion_host_fragment);
        guardar = view.findViewById(R.id.guardar_cambios_nombre);
        usuario = Usuario.getUser();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarNombre();
                Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public void guardarNombre() {
        final String nuevoNombre = nombre.getText().toString();
        final String nuevoApellido = apellido.getText().toString();
        String mensaje = "";
        boolean flag = true;

        if (nuevoNombre.isEmpty()) {

            mensaje += "\n"+"No haz ingresado el nombre"+"\n";
            flag = false;
        }
        if (nuevoApellido.isEmpty()) {

            mensaje += "\n"+"No haz ingresado el apellido"+"\n";
            flag = false;
        }
        if (flag) {

            String url;
            url = getString(R.string.ip)+"skylock/usuario/editar/"+nuevoNombre+"/"+nuevoApellido;
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            loadingDialog.isLoading(false);
                            if (response.equals("Exito")) {
                                usuario.setNombre(nuevoNombre);
                                usuario.setApellido(nuevoApellido);
                                Toast.makeText(getActivity(), "Nombre modificado", Toast.LENGTH_SHORT).show();
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
