package com.us.skyguardian.translock.offline;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.us.skyguardian.translock.DB.AppDatabase;
import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Permiso.Permiso;

import java.util.ArrayList;

public class OfflineViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Candado>> listaCandadosOffiline;
    private MutableLiveData<ArrayList<Permiso>> listaPermisosOffline;
    private MutableLiveData<AppDatabase> appDatabase;
    private MutableLiveData<Integer> userId;

    public OfflineViewModel() {
        this.listaCandadosOffiline = new MutableLiveData<>();
        this.listaPermisosOffline = new MutableLiveData<>();
        this.appDatabase = new MutableLiveData<>();
        this.userId = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId.setValue(userId);
    }

    public MutableLiveData<AppDatabase> getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase.setValue(appDatabase);
    }

    public MutableLiveData<ArrayList<Candado>> getListaCandadosOffiline() {
        return listaCandadosOffiline;
    }

    public void setListaCandadosOffiline(ArrayList<Candado> listaCandadosOffiline) {
        this.listaCandadosOffiline.setValue(listaCandadosOffiline);
    }

    public MutableLiveData<ArrayList<Permiso>> getListaPermisosOffline() {
        return listaPermisosOffline;
    }

    public void setListaPermisosOffline(ArrayList<Permiso> listaPermisosOffline) {
        this.listaPermisosOffline.setValue(listaPermisosOffline);
    }
}
