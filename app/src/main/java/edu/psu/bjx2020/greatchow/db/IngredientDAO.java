package edu.psu.bjx2020.greatchow.db;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface IngredientDAO {
    @Query("SELECT * FROM ingredient ORDER BY title COLLATE NOCASE")
    LiveData<List<Ingredient>> getAllAlphabetical();

    @Query("SELECT * FROM ingredient WHERE ingredientId = :ingredientId")
    Ingredient getById(int ingredientId);

    @Insert
    void insert(Ingredient... ingredients);

    @Update
    void update(Ingredient... ingredients);

    @Delete
    void delete(Ingredient... ingredients);

    @Query("DELETE FROM ingredient WHERE ingredientId = :ingredientId")
    void delete(int ingredientId);
}
