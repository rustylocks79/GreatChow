package edu.psu.bjx2020.greatchow.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId")
    public long recipeId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    public Recipe(long recipeId, String title, String description) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
    }
}
