package com.us.skyguardian.translock.DB.Historial;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "historial",
        indices = {@Index(value = "fecha", unique = true)})
public class Historial {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "fecha")
    public String fecha;

    @ColumnInfo(name = "idUsuario")
    public int idUsuario;

    @ColumnInfo(name = "idCandado")
    public int idCandado;

    @ColumnInfo(name = "idEvento")
    public int idEvento;

}
