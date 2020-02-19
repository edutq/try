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

public class usuariosCompartidosAdapter extends RecyclerView.Adapter<usuariosCompartidosAdapter.ViewHolderUsuarios> implements Filterable {

    ArrayList<usuariosCompartidosVG> usuarios;
    ArrayList<usuariosCompartidosVG> usuariosFull;

    public usuariosCompartidosAdapter(ArrayList<usuariosCompartidosVG> usuarios) {
        this.usuarios = usuarios;
        this.usuariosFull = new ArrayList<>(usuarios);
    }

    @NonNull
    @Override
    public ViewHolderUsuarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_usuarios_compartidos, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ViewHolderUsuarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUsuarios holder, int position) {

        holder.nombre.setText(usuarios.get(position).getNombre());
        holder.id.setText(Integer.toString(usuarios.get(position).getId()));
        holder.telefono.setText(usuarios.get(position).getTelefono());
        if(usuarios.get(position).isAdministrador()) {
            holder.tipo.setText("Administrador");
        } else {
            holder.tipo.setText("Básico");
        }

    }

    @Override
    public int getItemCount() {

        return usuarios.size();
    }

    @Override
    public Filter getFilter() {
        return usuariosCompartidosFilter;
    }

    private Filter usuariosCompartidosFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<usuariosCompartidosVG> filteredList = new ArrayList<usuariosCompartidosVG>();

            if (constraint == null || constraint.length()==0) {
                filteredList.addAll(usuariosFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (usuariosCompartidosVG usuario : usuariosFull) {
                    if (usuario.getNombre().toLowerCase().contains(filterPattern)) {
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

            usuarios.clear();
            usuarios.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolderUsuarios extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView tipo;
        TextView id;
        TextView telefono;

        public ViewHolderUsuarios(@NonNull View itemView) {

            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_usuario);
            tipo = itemView.findViewById(R.id.tipo_usuario);
            id = itemView.findViewById(R.id.id_usuario);
            telefono = itemView.findViewById(R.id.telefono_usuario);
        }

    }

}
