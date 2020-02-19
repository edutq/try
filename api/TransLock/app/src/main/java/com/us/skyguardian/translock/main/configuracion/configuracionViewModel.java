package com.us.skyguardian.translock.main.configuracion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class configuracionViewModel extends ViewModel {

    private MutableLiveData<Integer> idUsuario;
    private MutableLiveData<String> nombreUsuario;
    private MutableLiveData<String> apellidoUsuario;
    private MutableLiveData<Boolean> notificaciones;

    public configuracionViewModel () {

        this.idUsuario = new MutableLiveData<>();
        this.nombreUsuario = new MutableLiveData<>();
        this.apellidoUsuario = new MutableLiveData<>();
        this.notificaciones = new MutableLiveData<>();

    }

    public LiveData<Integer> getIdUsuario () {
        return this.idUsuario;
    }

    public void setIdUsuario (Integer idUsuario) {
        this.idUsuario.setValue(idUsuario);
    }

    public LiveData<String> getNombreUsuario () {
        return this.nombreUsuario;
    }

    public void setNombreUsuario (String nombreUsuario) {
        this.nombreUsuario.setValue(nombreUsuario);
    }

    public LiveData<String> getApellidoUsuario () {
        return this.apellidoUsuario;
    }

    public void setApellidoUsuario (String apellidoUsuario) {
        this.apellidoUsuario.setValue(apellidoUsuario);
    }

    public LiveData<Boolean> getNotifications () {
        return this.notificaciones;
    }

    public void setNotificacione (Boolean notificacione) {
        this.notificaciones.setValue(notificacione);
    }
}
