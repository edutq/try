package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

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

public class usuariosCompartidosSearchAdapter extends RecyclerView.Adapter<usuariosCompartidosSearchAdapter.viewHolderSearchUsuarios> implements Filterable {

    ArrayList<usuariosCompartidosSearchVG> listaUsuarios;
    ArrayList<usuariosCompartidosSearchVG> listaUsuariosFull;

    public usuariosCompartidosSearchAdapter(ArrayList<usuariosCompartidosSearchVG> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        this.listaUsuariosFull = new ArrayList<>(listaUsuarios);
        this.listaUsuarios.clear();
    }

    @NonNull
    @Override
    public viewHolderSearchUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_usuarios_search, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new viewHolderSearchUsuarios(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderSearchUsuarios holder, int position) {

        holder.nombre.setText(listaUsuarios.get(position).getNombre());
        holder.telefono.setText(listaUsuarios.get(position).getTelefono());
        holder.foto.setImageResource(listaUsuarios.get(position).getFoto());
        holder.id.setText(Integer.toString(listaUsuarios.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    @Override
    public Filter getFilter() {
        return usuariosFilter;
    }

    private Filter usuariosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<usuariosCompartidosSearchVG> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

            } else {

                String filterPattern = constraint.toString().toLowerCase().trim();

                for (usuariosCompartidosSearchVG usuario : listaUsuariosFull) {

                    if (usuario.getTelefono().toLowerCase().contains(filterPattern) ||
                        usuario.getNombre().toLowerCase().contains(filterPattern)) {

                            filteredList.add(usuario);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaUsuarios.clear();
            listaUsuarios.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class viewHolderSearchUsuarios extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView telefono;
        CircleImageView foto;
        TextView id;

        public viewHolderSearchUsuarios(View view){
            super(view);
            nombre = view.findViewById(R.id.nombre_usuario_search);
            telefono = view.findViewById(R.id.numero_telefono_search);
            foto = view.findViewById(R.id.foto_usuario_search);
            id = view.findViewById(R.id.id_usuario_search);
        }

    }

}
