package com.us.skyguardian.translock.DB.Permiso;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.us.skyguardian.translock.DB.Candado.Candado;


import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "permiso",

        indices = {@Index(value = "idCandado")})
public class Permiso {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "idCandado")
    public int idCandado;

    @ColumnInfo(name = "fechaInicio")
    public String fechaInicio;

    @ColumnInfo(name = "horaInicio")
    public String horaInicio;

    @ColumnInfo(name = "fechaFin")
    public String fechaFin;

    @ColumnInfo(name = "horaFin")
    public String horaFin;
}
