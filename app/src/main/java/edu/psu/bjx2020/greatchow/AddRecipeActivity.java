package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap image;

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
            EditText etName = findViewById(R.id.enter_title_et);

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
            ArrayList<String> stepsList = new ArrayList<>();
            EditText etSteps;
            int processnum = 2000;
            while(findViewById(processnum) != null) {
                etSteps = findViewById(processnum);
                stepsList.add(etSteps.getText().toString());
                processnum++;
            }

            EditText etNutrition = findViewById(R.id.enter_nutrition_et);
            CheckBox vgtrnCB = findViewById(R.id.vegetarian_cb);
            CheckBox vgnCB = findViewById(R.id.vegan_cb);

            ImageView imageView = findViewById(R.id.recipe_picture_iv);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();

            FirestoreGC firebaseGC = FirestoreGC.getInstance();
            firebaseGC.uploadImage(((BitmapDrawable) imageView.getDrawable()).getBitmap());

            Recipe recipe = new Recipe();
            String name = etName.getText().toString();
            if(name.equals("")) {
                Snackbar.make(view, "name can not be empty. ", Snackbar.LENGTH_SHORT).show();
                return;
            }
            recipe.setName(name);
            recipe.setOwnerID(firebaseGC.getOwnerID());
            recipe.setNutritionalInfo(etNutrition.getText().toString());
            recipe.setVegetarian(vgtrnCB.isChecked());
            recipe.setVegan(vgnCB.isChecked());
            if(ingredientList.isEmpty()) {
                Snackbar.make(view, "ingredients must contain at least one item. ", Snackbar.LENGTH_SHORT).show();
            }
            recipe.setIngredients(ingredientList);
            if(stepsList.isEmpty()) {
                Snackbar.make(view, "steps must contain at least one item.", Snackbar.LENGTH_SHORT).show();
            }
            recipe.setSteps(stepsList);
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

        Button btnAddImage = findViewById(R.id.add_image_button);
        btnAddImage.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            ImageView iv = findViewById(R.id.recipe_picture_iv);
            iv.setImageBitmap(image);
        }
    }
}
