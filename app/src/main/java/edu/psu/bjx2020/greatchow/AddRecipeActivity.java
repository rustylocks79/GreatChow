package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {
    int ingredientCounter;
    int processCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(view -> {
            EditText etTitle = findViewById(R.id.enter_title_et);

            ImageView iv = findViewById(R.id.recipe_picture_iv);
            Drawable recipeImg = iv.getDrawable();  //this should work, probably, test it

            //fill ingredientList
            ArrayList<String> ingredientList = new ArrayList<>();
            EditText etIngredient;
            int ingredientnum = 1000;
            while(findViewById(ingredientnum) != null) {
                etIngredient = findViewById(ingredientnum);
                ingredientList.add(etIngredient.getText().toString());
                ingredientnum++;
            }

            //fill processList
            ArrayList<String> processList = new ArrayList<>();
            EditText etProcess;
            int processnum = 2000;
            while(findViewById(processnum) != null) {
                etProcess = findViewById(processnum);
                processList.add(etProcess.getText().toString());
                processnum++;
            }

            EditText etNutrition = findViewById(R.id.enter_nutrition_et);
            CheckBox vgtrnCB = findViewById(R.id.vegetarian_cb);
            CheckBox vgnCB = findViewById(R.id.vegan_cb);

            FirestoreGC firebaseGC = FirestoreGC.getInstance();
            Recipe recipe = new Recipe();
            recipe.setName(etTitle.getText().toString());
            recipe.setOwnerID(firebaseGC.getOwnerID());
            recipe.setNutritionalInfo(etNutrition.getText().toString());
            recipe.setVegetarian(vgtrnCB.isChecked());
            recipe.setVegan(vgnCB.isChecked());
            recipe.setIngredients(ingredientList);
            recipe.setSteps(processList);
            firebaseGC.addRecipe(recipe);

            Intent intent = new Intent(AddRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //****************************************************************************************************//

        //Set Image Button
        //TODO: allow setting image
//        Button addImgButton = findViewById(R.id.add_image_button);
//        addImgButton.setOnClickListener(view -> {
//
//        });


        //****************************************************************************************************//

        //Dynamic Ingredient List
        ingredientCounter = 1000;
        findViewById(R.id.enter_ingredient_et_1).setId(ingredientCounter);
        LinearLayout llIngredientContainer = findViewById(R.id.ll_ingredients_container);
        Button ingredientAddButton = findViewById(R.id.new_ingredient_button);
        ingredientAddButton.setOnClickListener(view -> {
            EditText editText = new EditText(AddRecipeActivity.this);
            ingredientCounter++;
            editText.setId(ingredientCounter);
            editText.setHint(R.string.enter_ingredient);
            llIngredientContainer.addView(editText);

            //LOG STUFF
//                View v1 = findViewById(1000);
//                View vOther = findViewById(ingredientCounter);
//                Log.d("first", "" + v1.getId());
//                Log.d("other", "" + vOther.getId());
        });


        //****************************************************************************************************//

        //Dynamic Process List
        processCounter = 2000;
        findViewById(R.id.enter_process_et_1).setId(processCounter);
        LinearLayout llProcessContainer = findViewById(R.id.ll_process_container);
        Button processAddButton = findViewById(R.id.new_process_button);
        processAddButton.setOnClickListener(v -> {
            EditText editText = new EditText(AddRecipeActivity.this);
            processCounter++;
            editText.setId(processCounter);
            editText.setHint(R.string.enter_process_step);
            llProcessContainer.addView(editText);

            //LOG STUFF
//                View v1 = findViewById(2000);
//                View vOther = findViewById(processCounter);
//                Log.d("first", "" + v1.getId());
//                Log.d("other", "" + vOther.getId());
        });
    }



}
