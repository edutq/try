package com.us.skyguardian.translock.main.compartidos.usuariosCompartidos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class usuariosCompartidosViewModel extends ViewModel {

    private MutableLiveData<ArrayList<usuariosCompartidosVG>> listaUsuariosCompartidos;

    public usuariosCompartidosViewModel () {

        this.listaUsuariosCompartidos = new MutableLiveData<>();

    }

    public LiveData<ArrayList<usuariosCompartidosVG>> getListaUsuariosCompartidos () {
        return this.listaUsuariosCompartidos;
    }

    public void setListaUsuariosCompartidos (ArrayList<usuariosCompartidosVG> listaUsuariosCompartidos) {

        this.listaUsuariosCompartidos.setValue(listaUsuariosCompartidos);

    }

}
