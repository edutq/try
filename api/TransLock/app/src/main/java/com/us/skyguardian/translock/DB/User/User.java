package com.us.skyguardian.translock.DB.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario",
        indices = {@Index(value = "telefono", unique = true)})
public class User {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "telefono")
    public String telefono;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "apellido")
    public String apellido;

    public int getId() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
