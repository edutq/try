package com.us.skyguardian.translock.main.historial;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.Usuario;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class historialAdapter extends RecyclerView.Adapter<historialAdapter.ViewHolderHistorial> implements Filterable {

    ArrayList<historialVG> historial;
    ArrayList<historialVG> historialFull;

    public historialAdapter(ArrayList<historialVG> historial) {

        this.historial = historial;
        this.historialFull = new ArrayList<>(historial);
    }

    @NonNull
    @Override
    public ViewHolderHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_historial, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolderHistorial(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistorial holder, int position) {

        holder.nombre.setText(historial.get(position).getNombre_candado());
        holder.activador.setText(historial.get(position).getActivador());
        holder.evento.setText(historial.get(position).getEvento());
        holder.hora.setText(historial.get(position).getHora());
        holder.fecha.setText(historial.get(position).getFecha());
        Usuario usuario = Usuario.getUser();
        //Log.i("BLE", usuario.getNombre() + " " + usuario.getApellido());
        if (historial.get(position).getEvento().equals("APERTURA")) {
            holder.imagen.setImageResource(R.drawable.candado_abierto);
        } else if (historial.get(position).getEvento().equals("CIERRE")) {
            holder.imagen.setImageResource(R.drawable.candado);
        } else {
            if (historial.get(position).getAfectado().equals(usuario.getNombre() + " " + usuario.getApellido())) {
                holder.imagen.setImageResource(R.drawable.candado_compartido_por);
                holder.activador.setText("Por: " + historial.get(position).getActivador());
            } else {
                holder.imagen.setImageResource(R.drawable.candado_compartido_con);
                holder.activador.setText("Con: " + historial.get(position).getAfectado());
            }

        }
    }

    @Override
    public Filter getFilter() {
        return historialFilter;
    }

    private Filter historialFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<historialVG> filteredList = new ArrayList<historialVG>();

            if (constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(historialFull);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (historialVG historia : historialFull) {
                    if (historia.getNombre_candado().toLowerCase().contains(filteredPattern) ||
                        historia.getActivador().toLowerCase().contains(filteredPattern)||
                        historia.getEvento().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(historia);
                    }
                    if (historia.getAfectado() != null) {
                        if (historia.getAfectado().toLowerCase().contains(filteredPattern)) {
                            if (!filteredList.contains(historia)) {
                                filteredList.add(historia);
                            }
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            historial.clear();
            historial.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return historial.size();
    }

    public class ViewHolderHistorial extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView activador;
        TextView evento;
        ImageView imagen;
        TextView fecha;
        TextView hora;

        public ViewHolderHistorial(@NonNull View itemView) {

            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_candado_historial);
            activador = itemView.findViewById(R.id.activador_historial);
            evento = itemView.findViewById(R.id.evento_historial);
            fecha = itemView.findViewById(R.id.fecha_historial);
            hora = itemView.findViewById(R.id.hora_historial);
            imagen = itemView.findViewById(R.id.imagen_historial);
        }
    }
}
