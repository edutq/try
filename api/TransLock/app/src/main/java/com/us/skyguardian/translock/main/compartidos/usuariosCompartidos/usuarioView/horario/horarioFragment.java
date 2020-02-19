package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.horario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.CompartirCandadoViewModel;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.UsuarioViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class horarioFragment extends Fragment {

    Button guardar;
    TextView fecha_inicio;
    TextView hora_inicio;
    TextView fecha_fin;
    TextView hora_fin;
    LoadingDialog loadingDialog;
    boolean loading = false;
    loginDialog errorDialog;
    UsuarioViewModel usuarioViewModel;
    CompartirCandadoViewModel compartirCandadoViewModel;
    HorarioViewModel horarioViewModel;

    public horarioFragment() {
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
        View view = inflater.inflate(R.layout.fragment_horario, container, false);

        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_compartidos_host_fragment);
        errorDialog = new loginDialog();

        usuarioViewModel = ViewModelProviders.of(getActivity()).get(UsuarioViewModel.class);
        compartirCandadoViewModel = ViewModelProviders.of(getActivity()).get(CompartirCandadoViewModel.class);
        horarioViewModel = ViewModelProviders.of(getActivity()).get(HorarioViewModel.class);

        fecha_inicio = view.findViewById(R.id.fecha_de_horario);
        hora_inicio = view.findViewById(R.id.hora_de_horario);
        fecha_fin = view.findViewById(R.id.fecha_a_horario);
        hora_fin = view.findViewById(R.id.hora_a_horario);

        if (horarioViewModel.getId().getValue() != null) {
            fecha_inicio.setText(horarioViewModel.getFecha_inicio().getValue());
            hora_inicio.setText(horarioViewModel.getHora_inicio().getValue());
            fecha_fin.setText(horarioViewModel.getFecha_fin().getValue());
            hora_fin.setText(horarioViewModel.getHora_fin().getValue());
        }
        guardar = view.findViewById(R.id.guardar_permiso);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (horarioViewModel.getId().getValue() == null) {
                    guardarPermiso();
                } else {
                    modificarPermiso();
                }
                //Toast.makeText(getActivity(), "Guardar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void modificarPermiso() {
        if (validarPermiso()) {
            String url;
            url = getString(R.string.ip)+"skylock/candado_usuario/editar_permiso/" +
                    fecha_inicio.getText().toString() + "/" +
                    hora_inicio.getText().toString() + "/" +
                    fecha_fin.getText().toString() + "/" +
                    hora_fin.getText().toString() + "/" +
                    horarioViewModel.getId().getValue() + "/" +
                    compartirCandadoViewModel.getIdCandado().getValue();
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loadingDialog.isLoading(false);
                            if (response.equals("Exito")) {

                                Toast.makeText(getActivity(), "Permiso modificado!", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(guardar).popBackStack();

                            } else {
                                if (response.contains("ER_DUP_ENTRY")) {
                                    errorDialog.setTitulo("Error");
                                    errorDialog.setMensaje("El permiso ya está asignado a ese usuario");
                                    errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                                } else {
                                    errorDialog.setTitulo("Error");
                                    errorDialog.setMensaje(response);
                                    errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                                }

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
            errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
            Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarPermiso() {

        if (validarPermiso()) {
            String url;
            url = getString(R.string.ip)+"skylock/candado_usuario/otorgar_permiso/" +
                    fecha_inicio.getText().toString() + "/" +
                    hora_inicio.getText().toString() + "/" +
                    fecha_fin.getText().toString() + "/" +
                    hora_fin.getText().toString() + "/" +
                    usuarioViewModel.getIdUsuario().getValue() + "/" +
                    compartirCandadoViewModel.getIdCandado().getValue();
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loadingDialog.isLoading(false);
                            if (response.equals("Exito")) {

                                Toast.makeText(getActivity(), "Permiso otorgado!", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(guardar).popBackStack();

                            } else {
                                if (response.contains("ER_DUP_ENTRY")) {
                                    errorDialog.setTitulo("Error");
                                    errorDialog.setMensaje("El permiso ya está asignado a ese usuario");
                                    errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                                } else {
                                    errorDialog.setTitulo("Error");
                                    errorDialog.setMensaje(response);
                                    errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                                }

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
            errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
            Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validarPermiso() {

        boolean flag = true;
        String mensaje = "";
        String fi = fecha_inicio.getText().toString();
        String hi = hora_inicio.getText().toString();
        String ff = fecha_fin.getText().toString();
        String hf = hora_fin.getText().toString();

        if (fi.isEmpty()) {
            mensaje += "\n"+"No haz seleccionado la fecha de inicio"+"\n";
            flag=false;
        }
        if (hi.isEmpty()) {
            mensaje += "\n"+"No haz seleccionado la hora de inicio"+"\n";
            flag=false;
        }
        if (ff.isEmpty()) {
            mensaje += "\n"+"No haz seleccionado la fecha de fin"+"\n";
            flag=false;
        }
        if (hf.isEmpty()) {
            mensaje += "\n"+"No haz seleccionado la hora de fin"+"\n";
            flag=false;
        }
        if (flag) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date inicio = sdf.parse(fi+" "+hi);
                Date fin = sdf.parse(ff+" "+hf);
                if (inicio.getTime()>=fin.getTime()) {
                    mensaje += "\n"+"La fecha de inicio debe ser mayor a la fecha de fin del permiso"+"\n";
                    flag=false;
                }
                Log.i("BLE", ""+inicio.getTime());
            } catch (ParseException ex) {
                flag = false;
                Log.v("Exception", ex.getLocalizedMessage());
            }


        }
        if (!flag) {
            errorDialog.setTitulo("Error");
            errorDialog.setMensaje(mensaje);
        }
        return flag;


    }

}
