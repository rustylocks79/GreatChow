package edu.psu.bjx2020.greatchow.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Transaction
    @Query("SELECT * FROM recipes ORDER BY title COLLATE NOCASE, recipeId")
    LiveData<List<RecipeWithIngredients>> getAllAlphabetical();

    @Transaction
    @Query("SELECT * FROM recipes WHERE recipeId = :recipeId")
    RecipeWithIngredients getById(int recipeId);

    @Insert
    void insert(Recipe... recipes);

    @Update
    void update(Recipe... recipes);

    @Delete
    void delete(Recipe... recipes);

    @Query("DELETE FROM recipes WHERE recipeId = :recipeID")
    void delete(int recipeID);
}
