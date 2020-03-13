package edu.psu.bjx2020.greatchow.db;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface ContainsDAO {
    @Insert
    void insert(Contains... contains);
}
