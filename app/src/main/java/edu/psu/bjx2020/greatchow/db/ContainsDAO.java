package edu.psu.bjx2020.greatchow.db;

import androidx.room.*;

@Dao
public interface ContainsDAO {
    @Insert
    void insert(Contains... contains);

    @Update
    void update(Contains... contains);

    @Delete
    void delete(Contains... contains);

    @Query("DELETE FROM Contains WHERE recipeId = :recipeID and ingredientId = :ingredientId")
    void delete(int recipeID, int ingredientId);
}
