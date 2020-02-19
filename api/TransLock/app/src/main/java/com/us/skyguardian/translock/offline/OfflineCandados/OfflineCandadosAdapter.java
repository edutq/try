package com.us.skyguardian.translock.offline.OfflineCandados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OfflineCandadosAdapter extends RecyclerView.Adapter<OfflineCandadosAdapter.viewHolderOfflineCandados> {

    ArrayList<Candado> listaCandados;

    public OfflineCandadosAdapter(ArrayList<Candado> listaCandados) {
        this.listaCandados = listaCandados;
    }

    @NonNull
    @Override
    public viewHolderOfflineCandados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_candado_offline, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new OfflineCandadosAdapter.viewHolderOfflineCandados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderOfflineCandados holder, int position) {

        holder.nombre.setText(listaCandados.get(position).alias);
        holder.id.setText(Integer.toString(listaCandados.get(position).id));
        holder.imei.setText(listaCandados.get(position).macAddress);
        holder.identificador.setText(listaCandados.get(position).identificadorUnico);
        holder.marca.setText(listaCandados.get(position).marca+"");
        if (listaCandados.get(position).administrador == 1) {
            holder.administrador.setText("1");
        } else {
            holder.administrador.setText("0");
        }


    }

    @Override
    public int getItemCount() {
        return listaCandados.size();
    }

    public class viewHolderOfflineCandados extends RecyclerView.ViewHolder {

        TextView nombre;
        CircleImageView foto;
        TextView identificador;
        TextView id;
        TextView imei;
        TextView marca;
        TextView administrador;

        public viewHolderOfflineCandados(@NonNull View itemVIew) {
            super(itemVIew);
            nombre= itemVIew.findViewById(R.id.nombre_candado_offline);
            foto = itemVIew.findViewById(R.id.foto_candado_offline);
            identificador = itemVIew.findViewById(R.id.identificador_candado_offline);
            id = itemVIew.findViewById(R.id.id_candado_offline);
            imei = itemVIew.findViewById(R.id.imei_candado_offline);
            marca = itemVIew.findViewById(R.id.marca_candado_offline);
            administrador = itemVIew.findViewById(R.id.administrador_candado_offline);
        }
    }
}
