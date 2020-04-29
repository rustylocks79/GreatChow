package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class ViewRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");
        id = getIntent().getExtras().getString("id");

        if(recipe.getPathToImage() != null) {
            ImageView recipeIV = findViewById(R.id.recipe_picture_iv);
            FirestoreGC firestoreGC = FirestoreGC.getInstance();
            firestoreGC.getImageFromStorage(recipe.getPathToImage(), file -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
                recipeIV.setImageBitmap(bitmap);
            });
        }

            TextView recipeNameTV = findViewById(R.id.recipe_name_tv);
        recipeNameTV.setText(recipe.getName());

        LinearLayout llIngredientContainer = findViewById(R.id.ll_ingredients_container);
        for(int i=0; i<recipe.getIngredients().size(); i++) {
            TextView tv = new TextView(ViewRecipeActivity.this);
            tv.setText((i+1) + ") " + recipe.getIngredients().get(i));
            tv.setTextSize(18);
            llIngredientContainer.addView(tv);
        }

        LinearLayout llProcessContainer = findViewById(R.id.ll_process_container);
        for(int i=0; i<recipe.getSteps().size(); i++) {
            TextView tv = new TextView(ViewRecipeActivity.this);
            tv.setText((i+1) + ") " + recipe.getSteps().get(i));
            tv.setTextSize(18);
            llProcessContainer.addView(tv);
        }

        TextView nutrInfoTV = findViewById(R.id.nutrition_info_tv);
        nutrInfoTV.setText(recipe.getNutritionalInfo());

        TextView dietValueTV = findViewById(R.id.diet_category_value_tv);
        switch (recipe.getDiet()) {
            case 1:
                dietValueTV.setText("Vegetarian");
                break;
            case 2:
                dietValueTV.setText("Vegan");
                break;
            default:
                dietValueTV.setText("None");
        }

        FloatingActionButton fab = findViewById(R.id.view_fab);
        fab.setOnClickListener(view -> {
            FirestoreGC firestoreGC = FirestoreGC.getInstance();
            //TODO: make delete work. Currently the delete method of FirebaseGC does nothing.
            Snackbar.make(view, "Deleted " + recipe.getName(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            Intent intent = new Intent(ViewRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
