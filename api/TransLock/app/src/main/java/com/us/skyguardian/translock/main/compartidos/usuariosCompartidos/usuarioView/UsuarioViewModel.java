package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos.usuarioView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class UsuarioViewModel extends ViewModel {

    private MutableLiveData<String> nombreUsuario;
    private MutableLiveData<Integer> idUsuario;
    private MutableLiveData<Boolean> permisoAdministrador;
    private MutableLiveData<String> telefono;
    private MutableLiveData<ArrayList<usuarioHorarioVG>> horarioUsuario;

    public UsuarioViewModel() {

        this.nombreUsuario = new MutableLiveData<>();
        this.idUsuario = new MutableLiveData<>();
        this.permisoAdministrador = new MutableLiveData<>();
        this.horarioUsuario = new MutableLiveData<>();
        this.telefono = new MutableLiveData<>();

    }

    public MutableLiveData<String> getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.setValue(telefono);
    }

    public LiveData<String> getNombreUsuario () {
        return this.nombreUsuario;
    }

    public void setNombreUsuario (String nombre) {

        this.nombreUsuario.setValue(nombre);

    }

    public LiveData<Integer> getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario (Integer idUsuario) {
        this.idUsuario.setValue(idUsuario);
    }

    public LiveData<Boolean> getPermisoAdministrador() {
        return permisoAdministrador;
    }

    public void setPermisoAdministrador (Boolean permisoAdministrador) {
        this.permisoAdministrador.setValue(permisoAdministrador);
    }

    public LiveData<ArrayList<usuarioHorarioVG>> getHorarioUsuario() {
        return horarioUsuario;
    }

    public void setHorarioUsuario (ArrayList<usuarioHorarioVG> horarioUsuario) {
        this.horarioUsuario.setValue(horarioUsuario);
    }
}
