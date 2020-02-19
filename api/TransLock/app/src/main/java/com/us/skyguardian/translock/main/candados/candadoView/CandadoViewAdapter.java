package com.us.skyguardian.translock.main.candados.candadoView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.usuarioHorarioVG;

import java.util.ArrayList;

public class CandadoViewAdapter extends RecyclerView.Adapter<CandadoViewAdapter.viewHolderCandadoView> {

    ArrayList<usuarioHorarioVG> listaPermisos;

    public CandadoViewAdapter (ArrayList<usuarioHorarioVG> listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    @NonNull
    @Override
    public viewHolderCandadoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_permisos_candado, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(lp);

        return new viewHolderCandadoView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCandadoView holder, int position) {

        holder.fecha_inicio.setText(listaPermisos.get(position).getFecha_inicio());
        holder.hora_inicio.setText(listaPermisos.get(position).getHora_inicio());
        holder.fecha_fin.setText(listaPermisos.get(position).getFecha_fin());
        holder.hora_fin.setText(listaPermisos.get(position).getHora_fin());
    }

    @Override
    public int getItemCount() {
        return listaPermisos.size();
    }

    public class viewHolderCandadoView extends RecyclerView.ViewHolder {

        TextView fecha_inicio;
        TextView hora_inicio;
        TextView fecha_fin;
        TextView hora_fin;
        public viewHolderCandadoView(@NonNull View itemView) {
            super(itemView);
            fecha_inicio = itemView.findViewById(R.id.fecha_inicio_permiso_candado);
            hora_inicio = itemView.findViewById(R.id.hora_inicio_permiso_candado);
            fecha_fin = itemView.findViewById(R.id.fecha_fin_permiso_candado);
            hora_fin = itemView.findViewById(R.id.hora_fin_permiso_candado);
        }
    }
}
