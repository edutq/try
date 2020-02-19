package com.us.skyguardian.translock.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.us.skyguardian.translock.DB.Candado.Candado;
import com.us.skyguardian.translock.DB.Candado.CandadoDao;
import com.us.skyguardian.translock.DB.Historial.Historial;
import com.us.skyguardian.translock.DB.Historial.HistorialDao;
import com.us.skyguardian.translock.DB.Permiso.Permiso;
import com.us.skyguardian.translock.DB.Permiso.PermisoDao;
import com.us.skyguardian.translock.DB.User.User;
import com.us.skyguardian.translock.DB.User.UserDao;

@Database(entities = {User.class, Candado.class, Permiso.class, Historial.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PermisoDao permisoDao();
    public abstract CandadoDao candadoDao();
    public abstract HistorialDao historialDao();

}
