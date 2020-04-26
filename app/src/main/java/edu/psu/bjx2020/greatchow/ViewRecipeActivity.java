package edu.psu.bjx2020.greatchow;

import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class ViewRecipeActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");

        TextView recipeNameTV = findViewById(R.id.recipe_name_tv);
        recipeNameTV.setText(recipe.getName());

        LinearLayout llIngredientContainer = findViewById(R.id.ll_ingredients_container);
        for(int i=0; i<recipe.getIngredients().size(); i++) {
            TextView tv = new TextView(ViewRecipeActivity.this);
            tv.setText(recipe.getIngredients().get(i));
            tv.setTextSize(18);
            llIngredientContainer.addView(tv);
        }

        LinearLayout llProcessContainer = findViewById(R.id.ll_process_container);
        for(int i=0; i<recipe.getSteps().size(); i++) {
            TextView tv = new TextView(ViewRecipeActivity.this);
            tv.setText(recipe.getSteps().get(i));
            tv.setTextSize(18);
            llProcessContainer.addView(tv);
        }

        TextView nutrInfoTV = findViewById(R.id.nutrition_info_tv);
        nutrInfoTV.setText(recipe.getName());

        //TODO: in xml add TVs to show vegan/vegetarian

        FloatingActionButton fab = findViewById(R.id.view_fab);
        fab.setOnClickListener(view -> {
            // TODO: make fab delete current recipe
//            Snackbar.make(view, recipe.getName(), Snackbar.LENGTH_SHORT)
//                    .setAction("Action", null).show();
        });
    }
}
