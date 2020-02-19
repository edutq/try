package com.us.skyguardian.translock.offline.OfflineCandados.OfflineCandadoView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.main.candados.candadoView.CandadoViewAdapter;

import java.util.ArrayList;

public class OfflinePermisosAdapter extends RecyclerView.Adapter<OfflinePermisosAdapter.viewHolderPermisosOffline> {

    ArrayList<Permiso> listaPermisos;

    public OfflinePermisosAdapter (ArrayList<Permiso> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    @NonNull
    @Override
    public viewHolderPermisosOffline onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_permisos_candado, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(lp);

        return new OfflinePermisosAdapter.viewHolderPermisosOffline(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderPermisosOffline holder, int position) {
        holder.fecha_inicio.setText(listaPermisos.get(position).fechaInicio);
        holder.hora_inicio.setText(listaPermisos.get(position).horaInicio);
        holder.fecha_fin.setText(listaPermisos.get(position).fechaFin);
        holder.hora_fin.setText(listaPermisos.get(position).horaFin);
    }

    @Override
    public int getItemCount() {
        return listaPermisos.size();
    }

    public class viewHolderPermisosOffline extends RecyclerView.ViewHolder {
        TextView fecha_inicio;
        TextView hora_inicio;
        TextView fecha_fin;
        TextView hora_fin;
        public viewHolderPermisosOffline(@NonNull View itemView) {
            super(itemView);
            fecha_inicio = itemView.findViewById(R.id.fecha_inicio_permiso_candado);
            hora_inicio = itemView.findViewById(R.id.hora_inicio_permiso_candado);
            fecha_fin = itemView.findViewById(R.id.fecha_fin_permiso_candado);
            hora_fin = itemView.findViewById(R.id.hora_fin_permiso_candado);
        }
    }
}
