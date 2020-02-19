package com.us.skyguardian.translock.main.candados.candadoView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

public class CandadoViewViewModel extends ViewModel {

    private MutableLiveData<Integer> idCandado;
    private MutableLiveData<String> nombreCandado;
    private MutableLiveData<Integer> idCandadoOwner;
    private MutableLiveData<File> fotoCandado;
    private MutableLiveData<String> imei;
    private MutableLiveData<String> estado;
    private MutableLiveData<String> marca;
    private MutableLiveData<Boolean> admin;
    private MutableLiveData<String> identificador;

    public CandadoViewViewModel() {

        this.idCandado = new MutableLiveData<>();
        this.nombreCandado = new MutableLiveData<>();
        this.idCandadoOwner = new MutableLiveData<>();
        this.fotoCandado = new MutableLiveData<>();
        this.imei = new MutableLiveData<>();
        this.estado = new MutableLiveData<>();
        this.estado.setValue("Desconectado");
        this.marca = new MutableLiveData<>();
        this.admin = new MutableLiveData<>();
        this.identificador = new MutableLiveData<>();

    }

    public MutableLiveData<String> getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador.setValue(identificador);
    }

    public MutableLiveData<Boolean> getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin.setValue(admin);
    }

    public MutableLiveData<String> getMarca() {
        return marca;
    }

    public void setMarca (String marca) {
        this.marca.setValue(marca);
    }

    public LiveData<String> getEstado () {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado.setValue(estado);
    }

    public LiveData<Integer> getIdCandado() {
        return idCandado;
    }

    public void setIdCandado(Integer idCandado) {
        this.idCandado.setValue(idCandado);
    }

    public LiveData<String> getImei () {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei.setValue(imei);
    }

    public LiveData<String> getNombreCandado() {
        return nombreCandado;
    }

    public void setNombreCandado(String nombreCandado) {
        this.nombreCandado.setValue(nombreCandado);
    }

    public LiveData<Integer> getIdCandadoOwner() {
        return idCandadoOwner;
    }

    public void setIdCandadoOwner(Integer idCandadoOwner) {
        this.idCandadoOwner.setValue(idCandadoOwner);
    }

    public LiveData<File> getFotoCandado() {
        return fotoCandado;
    }

    public void setFotoCandado(File fotoCandado) {
        this.fotoCandado.setValue(fotoCandado);
    }
}
