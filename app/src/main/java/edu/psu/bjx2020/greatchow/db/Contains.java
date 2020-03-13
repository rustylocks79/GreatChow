package edu.psu.bjx2020.greatchow.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"recipeId", "ingredientId"})
public class Contains {
    public long recipeId;
    public long ingredientId;

    @ColumnInfo(name = "Quantity")
    public String quantity;

    public Contains(long recipeId, long ingredientId, String quantity) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }
}
