package com.us.skyguardian.translock.main.compartidos.compartirCandado;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class candadoSearchAdapter extends RecyclerView.Adapter<candadoSearchAdapter.viewHolderCandadoSearch> implements Filterable {

    ArrayList<candadoSearchVG> listaCandados;
    ArrayList<candadoSearchVG> listaCandadosFull;

    public candadoSearchAdapter(ArrayList<candadoSearchVG> listaCandados) {
        this.listaCandados = listaCandados;
        listaCandadosFull = new ArrayList<>(listaCandados);
    }

    @NonNull
    @Override
    public viewHolderCandadoSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_candados_search, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new viewHolderCandadoSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderCandadoSearch holder, int position) {

        holder.nombre.setText(listaCandados.get(position).getNombre());
        holder.foto.setImageResource(listaCandados.get(position).getFoto());
        holder.id.setText(Integer.toString(listaCandados.get(position).getId()));
        holder.identificador.setText(listaCandados.get(position).getIdentificador());

    }

    @Override
    public int getItemCount() {
        return listaCandados.size();
    }

    @Override
    public Filter getFilter() {
        return candadosFilter;
    }

    private Filter candadosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<candadoSearchVG> filteredList = new ArrayList<candadoSearchVG>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaCandadosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (candadoSearchVG candado : listaCandadosFull) {

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

    public class viewHolderCandadoSearch extends RecyclerView.ViewHolder{

        TextView nombre;
        CircleImageView foto;
        TextView id;
        TextView identificador;

        public viewHolderCandadoSearch(@NonNull View itemView) {
            super(itemView);
            nombre =itemView.findViewById(R.id.nombre_dialog_candado);
            foto = itemView.findViewById(R.id.foto_dialog_candado);
            id = itemView.findViewById(R.id.id_dialog_candado);
            identificador = itemView.findViewById(R.id.identificador_candado_search);
        }
    }


}
