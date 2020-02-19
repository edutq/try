package com.us.skyguardian.translock.main.candados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;
import com.us.skyguardian.translock.main.compartidos.compartirCandado.candadoSearchVG;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CandadosAdapter extends RecyclerView.Adapter<CandadosAdapter.viewHolderCandados> implements Filterable {

    ArrayList<CandadosVG> listaCandados;
    ArrayList<CandadosVG> listaCandadosFull;

    public CandadosAdapter(ArrayList<CandadosVG> listaCandados) {
        this.listaCandados = listaCandados;
        listaCandadosFull = new ArrayList<>(listaCandados);
    }


    @NonNull
    @Override
    public viewHolderCandados onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_candado, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new viewHolderCandados(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCandados holder, int position) {

        holder.nombre.setText(listaCandados.get(position).getNombre());
        holder.foto.setImageResource(listaCandados.get(position).getFoto());
        holder.icono.setImageResource(listaCandados.get(position).getIcono());
        holder.id.setText(Integer.toString(listaCandados.get(position).getId()));
        holder.imei.setText(listaCandados.get(position).getImei());
        holder.marca.setText(listaCandados.get(position).getMarca());
        holder.identificador.setText(listaCandados.get(position).getIdentificador());
        if (listaCandados.get(position).isAdmin()) {
            holder.administrador.setText("1");
        } else {
            holder.administrador.setText("0");
        }

    }

    @Override
    public int getItemCount () {
        return listaCandados.size();
    }

    @Override
    public Filter getFilter() {
        return candadosFilter;
    }

    private Filter candadosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<CandadosVG> filteredList = new ArrayList<CandadosVG>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaCandadosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CandadosVG candado : listaCandadosFull) {

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

            listaCandados.clear();
            listaCandados.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class viewHolderCandados extends RecyclerView.ViewHolder {

        TextView nombre;
        CircleImageView foto;
        ImageView icono;
        TextView id;
        TextView imei;
        TextView marca;
        TextView administrador;
        TextView identificador;

        public viewHolderCandados (@NonNull View itemVIew) {

            super(itemVIew);
            nombre= itemVIew.findViewById(R.id.nombre_candado);
            foto = itemVIew.findViewById(R.id.foto_candado);
            icono = itemVIew.findViewById(R.id.logo_condicion);
            id = itemVIew.findViewById(R.id.id_candado);
            imei = itemVIew.findViewById(R.id.imei_candado);
            marca = itemVIew.findViewById(R.id.marca_candado);
            administrador = itemVIew.findViewById(R.id.administrador_candado);
            identificador = itemVIew.findViewById(R.id.identificador_candado);
        }

    }

}
