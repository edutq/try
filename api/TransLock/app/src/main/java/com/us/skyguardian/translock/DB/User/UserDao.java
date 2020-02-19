package com.us.skyguardian.translock.DB.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class UserDao {

    @Transaction
    public void cleanInsertTransaction(User user) {
        drop();
        insert(user);
    }

    @Query("SELECT * FROM usuario WHERE telefono = :telefonoUser")
    public abstract User findByPhone(String telefonoUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(User user);

    @Delete
    public abstract void delete(User user);

    @Query("DELETE FROM usuario")
    public abstract  void drop();
}
