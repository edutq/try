package com.us.skyguardian.translock.main.configuracion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.Usuario;
import com.us.skyguardian.translock.login.loginDialog;

import java.util.ArrayList;

public class ConfiguracionFragment extends Fragment {

    ArrayList<String> data;
    Usuario usuario;
    TextView nombre;
    TextView telefono;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        nombre = view.findViewById(R.id.nombre_usuario_configuracion);
        telefono = view.findViewById(R.id.telefono_configuracion);
        usuario = Usuario.getUser();
        nombre.setText(usuario.getNombre() +" "+ usuario.getApellido());
        telefono.setText(usuario.getTelefono());

        RecyclerView lista = view.findViewById(R.id.listado);
        lista.setLayoutManager(new LinearLayoutManager(getContext()));

        data = new ArrayList<String>();

        ConfiguracionAdapter adapter = new ConfiguracionAdapter(data);
        lista.setAdapter(adapter);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}