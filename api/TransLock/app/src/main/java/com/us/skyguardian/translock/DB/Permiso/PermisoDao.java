package com.us.skyguardian.translock.DB.Permiso;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class PermisoDao {

    @Transaction
    public void cleanInsertTransaction (List<Permiso> permisos) {
        drop();
        insertAll(permisos);
    }

    @Query("DELETE FROM permiso")
    public abstract void drop();

    @Query("SELECT * FROM permiso WHERE idCandado = :candadoId")
    public abstract List<Permiso> getAllByCandado (int candadoId);

    @Query("SELECT * FROM permiso")
    public abstract List<Permiso> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<Permiso> permisos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert (Permiso permiso);

    @Delete
    public abstract void delete (Permiso permiso);
}
