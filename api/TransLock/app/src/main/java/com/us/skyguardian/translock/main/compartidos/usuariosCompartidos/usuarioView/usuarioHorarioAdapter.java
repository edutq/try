package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;

import java.util.ArrayList;

public class usuarioHorarioAdapter extends RecyclerView.Adapter<usuarioHorarioAdapter.viewHolderHorario> {

    ArrayList<usuarioHorarioVG> horarios;

    public usuarioHorarioAdapter(ArrayList<usuarioHorarioVG> horarios) {
        this.horarios = horarios;
    }

    @NonNull
    @Override
    public viewHolderHorario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_horario, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        view.setLayoutParams(lp);

        return new viewHolderHorario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderHorario holder, int position) {

        holder.fecha_inicio.setText(horarios.get(position).getFecha_inicio());
        holder.hora_inicio.setText(horarios.get(position).getHora_inicio());
        holder.fecha_fin.setText(horarios.get(position).getFecha_fin());
        holder.hora_fin.setText(horarios.get(position).getHora_fin());
        holder.id.setText(""+horarios.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

    public class viewHolderHorario extends RecyclerView.ViewHolder {

        TextView fecha_inicio;
        TextView hora_inicio;
        TextView fecha_fin;
        TextView hora_fin;
        TextView id;

        public viewHolderHorario(@NonNull View itemView) {
            super(itemView);
            fecha_inicio = itemView.findViewById(R.id.fecha_inicio_permiso_usuario);
            hora_inicio = itemView.findViewById(R.id.hora_inicio_permiso_usuario);
            fecha_fin = itemView.findViewById(R.id.fecha_fin_permiso_usuario);
            hora_fin = itemView.findViewById(R.id.hora_fin_permiso_usuario);
            id = itemView.findViewById(R.id.id_permiso_usuario_candado);
        }
    }

    public ArrayList<usuarioHorarioVG> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<usuarioHorarioVG> horarios) {
        this.horarios = horarios;
    }
}
