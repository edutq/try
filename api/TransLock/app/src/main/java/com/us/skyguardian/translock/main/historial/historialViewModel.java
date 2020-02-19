package com.us.skyguardian.translock.main.historial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class historialViewModel extends ViewModel {

    private MutableLiveData<ArrayList<historialVG>> listaHistorial;

    public historialViewModel() {
        this.listaHistorial = new MutableLiveData<>();
    }

    public LiveData<ArrayList<historialVG>> getListaHistorial () {
        return this.listaHistorial;

    }

    public void setListaHistorial (ArrayList<historialVG> listaHistorial) {
        this.listaHistorial.setValue(listaHistorial);

    }
}
