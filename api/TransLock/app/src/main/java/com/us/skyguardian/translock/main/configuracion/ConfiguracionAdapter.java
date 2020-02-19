package com.us.skyguardian.translock.main.configuracion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.us.skyguardian.translock.R;

import java.util.ArrayList;

public class ConfiguracionAdapter extends RecyclerView.Adapter<ConfiguracionAdapter.ViewHolderLista> {

    private ArrayList<String> data;

    public ConfiguracionAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolderLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_configuracion, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolderLista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLista holder, int position) {
        holder.dato.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderLista extends RecyclerView.ViewHolder {

        TextView dato;

        public ViewHolderLista(@NonNull View itemView) {

            super(itemView);
            dato = itemView.findViewById(R.id.textconfiguracion);
        }
    }
}
