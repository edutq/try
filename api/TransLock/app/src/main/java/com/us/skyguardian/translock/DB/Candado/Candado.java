package com.us.skyguardian.translock.DB.Candado;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "candado",
        indices = {@Index(value = "identificadorUnico", unique = true)})
public class Candado {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "alias")
    public String alias;

    @ColumnInfo(name = "macAddress")
    public String macAddress;

    @ColumnInfo(name = "marca")
    public int marca;

    @ColumnInfo(name = "identificadorUnico")
    public String identificadorUnico;

    @ColumnInfo(name = "administrador")
    public int administrador;
}