package com.us.skyguardian.translock.main.compartidos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class compartidosViewModel extends ViewModel {

    private MutableLiveData<ArrayList<compartidosVG>> listaCompartidos;

    public compartidosViewModel() {

        this.listaCompartidos = new MutableLiveData<>();

    }

    public LiveData<ArrayList<compartidosVG>> getListaCompartidos () {
        return listaCompartidos;
    }

    public void setListaCompartidos (ArrayList<compartidosVG> listaCompartidos) {

        this.listaCompartidos.setValue(listaCompartidos);

    }
}
