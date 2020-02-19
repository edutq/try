package com.us.skyguardian.translock.DB.Candado;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class CandadoDao {

    @Transaction
    public void cleanInsertTransaction(List<Candado> candados) {
        drop();
        insertAll(candados);
    }

    @Query("SELECT * FROM candado")
    public abstract List<Candado> getAll();

    @Query("DELETE FROM candado")
    public abstract void drop();

    @Insert
    public abstract void insert (Candado candado);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public  abstract void insertAll(List<Candado> candados);

    @Delete
    public  abstract void delete(Candado candado);

}
