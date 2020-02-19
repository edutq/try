package com.us.skyguardian.translock.DB.Historial;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class HistorialDao {

    @Transaction
    public List<Historial> getCleanTransaction() {
        List<Historial> listahistorial = getAll();
        drop();
        return listahistorial;
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Historial historial);

    @Query("SELECT * FROM historial")
    public abstract List<Historial> getAll();

    @Query("DELETE FROM historial")
    public abstract void drop();

}
