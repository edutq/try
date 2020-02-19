package com.us.skyguardian.translock.main.compartidos.compartirCandado;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;

public class CompartirCandadoViewModel extends ViewModel {

    private MutableLiveData<String> nombreCandado;
    private MutableLiveData<File> fotoCandado;
    private MutableLiveData<Integer> idCandado;

    public CompartirCandadoViewModel() {

        nombreCandado = new MutableLiveData<>();
        fotoCandado = new MutableLiveData<>();
        idCandado = new MutableLiveData<>();
        idCandado.setValue(null);
    }


    public LiveData<String> getnombreCandado () {
        return nombreCandado;
    }

    public void setNombreCandado (String s) {

        nombreCandado.setValue(s);

    }

    public LiveData<Integer> getIdCandado () {
        return idCandado;
    }

    public void setIdCandado (Integer id) {
        idCandado.setValue(id);
    }

    public LiveData<File> getFotoCandado () {

        return this.fotoCandado;

    }

    public void setFotoCandado (File fotoCandado) {
        this.fotoCandado.setValue(fotoCandado);
    }

}
