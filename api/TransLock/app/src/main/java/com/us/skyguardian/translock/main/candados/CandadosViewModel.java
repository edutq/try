package com.us.skyguardian.translock.main.candados;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.us.skyguardian.translock.DB.AppDatabase;
import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Candado.CandadoDao;
import com.us.skyguardian.translock.DB.User.UserDao;

import java.util.ArrayList;
import java.util.List;

public class CandadosViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CandadosVG>> listaCandados;
    private List<Candado> Candados;
    private AppDatabase appDatabase;

    public CandadosViewModel () {
        this.listaCandados = new MutableLiveData<>();
    }

    public void fillCandadosList (ArrayList<CandadosVG> listaCandados) {
        this.listaCandados.setValue(listaCandados);
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public LiveData<ArrayList<CandadosVG>> getListaCandados () {
        return this.listaCandados;
    }

    public List<Candado> getCandados() {
        return Candados;
    }

    public void setCandados(List<Candado> candados) {
        Candados = candados;
    }


}
