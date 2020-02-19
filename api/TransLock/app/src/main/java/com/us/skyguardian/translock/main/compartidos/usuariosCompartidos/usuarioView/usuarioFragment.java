package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.us.skyguardian.translock.LoadingDialog;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.SingletonRequestQueue;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.loginDialog;
import com.us.skyguardian.translock.main.candados.CandadosAdapter;
import com.us.skyguardian.translock.main.candados.CandadosVG;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.CompartirCandadoViewModel;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.horario.HorarioViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class usuarioFragment extends Fragment {

    ArrayList<usuarioHorarioVG> lista_horario;
    Button borrar;
    AlertDialog aviso;
    TextView nombre;
    TextView telefono;
    TextView id;
    Switch administrador;
    loginDialog errorDialog;
    UsuarioViewModel usuarioViewModel;
    CompartirCandadoViewModel compartirCandadoViewModel;
    LoadingDialog loadingDialog;
    RecyclerView horario;
    LinearLayout agregarPermiso;
    View divider;
    CircleImageView eliminar;
    HorarioViewModel horarioViewModel;
    public usuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        horarioViewModel.setId(null);
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

        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        loadingDialog = new LoadingDialog(getActivity(), R.id.nav_compartidos_host_fragment);
        errorDialog = new loginDialog();
        agregarPermiso = view.findViewById(R.id.panel_agregar_permiso);
        usuarioViewModel = ViewModelProviders.of(getActivity()).get(UsuarioViewModel.class);
        horarioViewModel = ViewModelProviders.of(getActivity()).get(HorarioViewModel.class);
        compartirCandadoViewModel = ViewModelProviders.of(getActivity()).get(CompartirCandadoViewModel.class);
        divider = view.findViewById(R.id.divider_agregar_permiso);
        nombre = view.findViewById(R.id.nombre_perfil);
        telefono = view.findViewById(R.id.numero_perfil);
        administrador = view.findViewById(R.id.switch_administrador);
        id = view.findViewById(R.id.id_perfil);

        nombre.setText(usuarioViewModel.getNombreUsuario().getValue());
        telefono.setText(usuarioViewModel.getTelefono().getValue());
        id.setText(Integer.toString(usuarioViewModel.getIdUsuario().getValue()));
        horario = view.findViewById(R.id.lista_horarios);
        if (usuarioViewModel.getPermisoAdministrador().getValue()) {
            administrador.setChecked(true);
            horario.setVisibility(View.GONE);
            agregarPermiso.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else {
            administrador.setChecked(false);
        }


        administrador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String url;
                if (isChecked) {
                    url = getString(R.string.ip)+"skylock/candado/modificar_administracion/"+
                                                    id.getText().toString()+"/"+
                                                    compartirCandadoViewModel.getIdCandado().getValue()+
                                                    "/1";
                } else {
                    url = getString(R.string.ip)+"skylock/candado/modificar_administracion/"+
                                                    id.getText().toString()+"/"+
                                                    compartirCandadoViewModel.getIdCandado().getValue()+
                                                    "/0";
                }
                modificarAdministracion(url, isChecked);
            }
        });
        horario.setLayoutManager(new LinearLayoutManager(getContext()));

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());

        builder.setTitle("Aviso!").setMessage("¿Seguro que desea dejar de compartir el candado con: " + nombre.getText().toString() + "?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrarUsuario();
            }
        });

        aviso = builder.create();

        lista_horario = new ArrayList<usuarioHorarioVG>();

        String url = getString(R.string.ip)+"skylock/candado_usuario/ver_permisos/"+
                                            usuarioViewModel.getIdUsuario().getValue()+"/"+
                                            compartirCandadoViewModel.getIdCandado().getValue();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject horario = response.getJSONObject(i);

                                lista_horario.add(new usuarioHorarioVG(horario.getInt("id"),
                                                                horario.getString("fecha_inicio"),
                                                                horario.getString("hora_inicio"),
                                                                horario.getString("fecha_fin"),
                                                                horario.getString("hora_fin")));

                            }

                            usuarioHorarioAdapter adapter = new usuarioHorarioAdapter(lista_horario);
                            horario.setAdapter(adapter);

                            loadingDialog.isLoading(false);

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




        borrar = view.findViewById(R.id.boton_borrar_usuario_compartido);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aviso.show();

            }
        });

        return view;
    }

    public void modificarAdministracion(String url, final boolean isChecked) {
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadingDialog.isLoading(false);
                        if (response.equals("Exito")) {
                            if (isChecked) {
                                horario.setVisibility(View.GONE);
                                agregarPermiso.setVisibility(View.GONE);
                                divider.setVisibility(View.GONE);
                            } else {
                                horario.setVisibility(View.VISIBLE);
                                agregarPermiso.setVisibility(View.VISIBLE);
                                divider.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(getActivity(), "Modificación Exitosa", Toast.LENGTH_SHORT).show();

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
                        errorDialog.setTitulo("Error");
                        errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                "Por favor intente más tarde o revise su conexión a internet.");
                        errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                        loadingDialog.isLoading(false);
                    }
                }
        );
        loadingDialog.isLoading(true);
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    public void borrarUsuario() {
        String url;
        url = getString(R.string.ip)+"skylock/candado/dejar_de_compartir/"+id.getText().toString()+"/"+compartirCandadoViewModel.getIdCandado().getValue();
        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadingDialog.isLoading(false);
                        if (response.equals("Exito")) {

                            Toast.makeText(getActivity(), "Borrado Exitoso", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(borrar).popBackStack();

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
                        errorDialog.setTitulo("Error");
                        errorDialog.setMensaje("Hubo un error al conectarse con el servidor.\n" +
                                "Por favor intente más tarde o revise su conexión a internet.");
                        errorDialog.show(getActivity().getSupportFragmentManager(), "Error");
                        loadingDialog.isLoading(false);
                    }
                }
        );
        loadingDialog.isLoading(true);
        SingletonRequestQueue.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }

}
