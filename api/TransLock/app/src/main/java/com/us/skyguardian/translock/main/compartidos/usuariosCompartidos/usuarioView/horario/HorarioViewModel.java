package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView.horario;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Time;
import java.util.Date;

public class HorarioViewModel extends ViewModel {

    private MutableLiveData<String> fecha_inicio;
    private MutableLiveData<String> fecha_fin;
    private MutableLiveData<String> hora_inicio;
    private MutableLiveData<String> hora_fin;
    private MutableLiveData<Integer> id;

    public HorarioViewModel() {
        this.fecha_fin = new MutableLiveData<>();
        this.fecha_inicio = new MutableLiveData<>();
        this.hora_fin = new MutableLiveData<>();
        this.hora_inicio = new MutableLiveData<>();
        this.id = new MutableLiveData<>();
    }

    public MutableLiveData<String> getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio.setValue(fecha_inicio);
    }

    public MutableLiveData<String> getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin.setValue(fecha_fin);
    }

    public MutableLiveData<String> getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio.setValue(hora_inicio);
    }

    public MutableLiveData<String> getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin.setValue(hora_fin);
    }

    public MutableLiveData<Integer> getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id.setValue(id);
    }
}
