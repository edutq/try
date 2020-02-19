package com.us.skyguardian.translock.main.compartidos.compartirCandado;

import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.appcompat.widget.SwitchCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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


public class compartirCandadoFragment extends Fragment {

    private CompartirCandadoViewModel compartirCandadoViewModel;
    TextView nombre;
    LinearLayoutCompat candadoSeleccionado;
    LinearLayoutCompat usuarioSeleccionado;
    Button compartir;
    loginDialog errorDialog;
    TextView idCandado;
    TextView idUsuario;
    SwitchCompat admin;
    int adminvalues = 0;
    LoadingDialog loadingDialog;
    public compartirCandadoFragment() {
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

        compartirCandadoViewModel = ViewModelProviders.of(getActivity()).get(CompartirCandadoViewModel.class);
        // Inflate the layout for this fragment
        errorDialog = new loginDialog();
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_compartidos_host_fragment);
        View view = inflater.inflate(R.layout.fragment_compartir_candado, container, false);
        candadoSeleccionado = view.findViewById(R.id.candado_seleccionado);
        usuarioSeleccionado = view.findViewById(R.id.usuario_seleccionado);
        admin = view.findViewById(R.id.switch_administrador_usuario_seleccionado);

        admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adminvalues = 1;
                } else {
                    adminvalues = 0;
                }
                //Toast.makeText(getActivity(), ""+adminvalues, Toast.LENGTH_SHORT).show();
            }
        });


        nombre = view.findViewById(R.id.nombre_candado_seleccionado);
        idCandado = view.findViewById(R.id.id_candado_seleccionado);
        idUsuario = view.findViewById(R.id.id_usuario_seleccionado);
        if (compartirCandadoViewModel.getIdCandado().getValue() != null) {
            candadoSeleccionado.setVisibility(View.VISIBLE);
            compartirCandadoViewModel.getnombreCandado().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    nombre.setText(s);
                }
            });
            compartirCandadoViewModel.getIdCandado().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    idCandado.setText(Integer.toString(integer));
                }
            });
        }

        compartir = view.findViewById(R.id.compartir_candado);
        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = "";
                boolean flag = true;

                if (candadoSeleccionado.getVisibility() == View.GONE) {

                    mensaje += "\n"+"No haz seleccionado un candado"+"\n";
                    flag = false;
                }
                if (usuarioSeleccionado.getVisibility() == View.GONE) {

                    mensaje += "\n"+"No haz seleccionado un usuario"+"\n";
                    flag = false;
                }
                if (flag) {

                    String url;
                    url = getString(R.string.ip)+"skylock/candado/compartir/"+
                            idUsuario.getText().toString()+"/"+
                            idCandado.getText().toString()+"/"+
                            adminvalues;
                    StringRequest request = new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    loadingDialog.isLoading(false);
                                    if (response.equals("Exito")) {

                                        Toast.makeText(getActivity(), "Candado Compartido!", Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(compartir).popBackStack();

                                    } else {
                                        if (response.contains("ER_DUP_ENTRY")) {
                                            errorDialog.setTitulo("Error");
                                            errorDialog.setMensaje("El candado ya está compartido con este usuario");
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
                    errorDialog.setTitulo("Error");
                    errorDialog.setMensaje(mensaje);
                    errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                }
            }
        });

        return view;
    }


}
