package edu.psu.bjx2020.greatchow.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredientId")
    public long ingredientId;

    @ColumnInfo(name = "title")
    public String title;

    public Ingredient(long ingredientId, String title) {
        this.ingredientId = ingredientId;
        this.title = title;
    }
}
