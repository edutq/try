package com.us.skyguardian.translock.main.compartidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;

import java.util.ArrayList;
import java.util.List;

public class compartidosAdapter extends RecyclerView.Adapter<compartidosAdapter.viewHolderCompartidos> implements Filterable {

    ArrayList<compartidosVG> listaCompartidos;
    ArrayList<compartidosVG> listaCompartidosFull;

    public compartidosAdapter(ArrayList<compartidosVG> listaCompartidos) {

        this.listaCompartidos = listaCompartidos;
        this.listaCompartidosFull = new ArrayList<>(listaCompartidos);

    }

    @NonNull
    @Override
    public viewHolderCompartidos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_candados_compartidos, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new viewHolderCompartidos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCompartidos holder, int position) {

        holder.nombreCandado.setText(listaCompartidos.get(position).getNombre());
        holder.cantidadUsuarios.setText(Integer.toString(listaCompartidos.get(position).getCantidadCopartidos()));
        holder.id.setText(Integer.toString(listaCompartidos.get(position).getId()));
        holder.imei.setText(listaCompartidos.get(position).getImei());
        holder.identificador.setText(listaCompartidos.get(position).getIdentificador());

    }

    @Override
    public int getItemCount() {
        return listaCompartidos.size();
    }

    @Override
    public Filter getFilter() {
        return compartidosFilter;
    }

    private Filter compartidosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<compartidosVG> filteredList = new ArrayList<compartidosVG>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaCompartidosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (compartidosVG candado : listaCompartidosFull) {
                    if (candado.getNombre().toLowerCase().contains(filterPattern) ||
                        candado.getIdentificador().toLowerCase().contains(filterPattern)) {
                        filteredList.add(candado);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaCompartidos.clear();
            listaCompartidos.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class viewHolderCompartidos extends RecyclerView.ViewHolder{

        TextView nombreCandado;
        TextView cantidadUsuarios;
        TextView id;
        TextView imei;
        TextView identificador;

        public viewHolderCompartidos(@NonNull View itemView) {

            super(itemView);
            nombreCandado = itemView.findViewById(R.id.nombre_candado_compartido);
            cantidadUsuarios = itemView.findViewById(R.id.cantidad_candado_compartido);
            id = itemView.findViewById(R.id.id_candado_compartido);
            imei = itemView.findViewById(R.id.imei_candado_compartido);
            identificador = itemView.findViewById(R.id.identificador_candado_compartido);
        }
    }
}
