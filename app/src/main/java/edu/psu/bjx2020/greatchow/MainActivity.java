package edu.psu.bjx2020.greatchow;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.psu.bjx2020.greatchow.db.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Needed to insure that database instance is created.
        AppDatabase rdb = AppDatabase.getDatabase(getApplicationContext());

        AppDatabase.insert(new Recipe(1, "Pancakes", "Breakfast"));
        AppDatabase.insert(new Ingredient(1, "Butter"));
        AppDatabase.insert(new Contains(1, 1, "2/3 cup"));
        AppDatabase.insert(new Recipe(2, "Scrambled Eggs", "More Breakfast"));
        AppDatabase.insert(new Recipe(3, "General Tso's Chicken", "Good but not Breakfast"));

        AppDatabase.getRecipe(1, new AppDatabase.RecipeListener() {
            @Override
            public void onRecipeReturned(RecipeWithIngredients recipe) {
                Log.d("Database", recipe.recipe.title);
                Log.d("Database", recipe.ingredients.get(0).title);
            }
        });
    }
}
