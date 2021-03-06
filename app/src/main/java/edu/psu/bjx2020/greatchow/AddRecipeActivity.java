package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    private static final String TAG = "AddRecipeActivity";

    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_STORAGE = 1;
    private static final int RC_SIGN_IN = 123;
    private ImageView ivRecipe;

    int ingredientCounter;
    int processCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //firestore
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .build(),
                    RC_SIGN_IN);
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        ivRecipe = findViewById(R.id.recipe_picture_iv);

        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(view -> {
            EditText etName = findViewById(R.id.enter_title_et);
            String name = etName.getText().toString();
            if (name.equals("")) {
                Snackbar.make(view, "name can not be empty. ", Snackbar.LENGTH_SHORT).show();
                return;
            }

            ArrayList<String> ingredientList = new ArrayList<>();
            EditText etIngredient;
            int ingredientnum = 1000;
            while (findViewById(ingredientnum) != null) {
                etIngredient = findViewById(ingredientnum);
                ingredientList.add(etIngredient.getText().toString());
                ingredientnum++;
            }
            if (ingredientList.isEmpty()) {
                Snackbar.make(view, "ingredients must contain at least one item. ", Snackbar.LENGTH_SHORT).show();
            }

            ArrayList<String> stepsList = new ArrayList<>();
            EditText etSteps;
            int stepnum = 2000;
            while (findViewById(stepnum) != null) {
                etSteps = findViewById(stepnum);
                stepsList.add(etSteps.getText().toString());
                stepnum++;
            }
            if (stepsList.isEmpty()) {
                Snackbar.make(view, "steps must contain at least one item.", Snackbar.LENGTH_SHORT).show();
            }

            EditText etNutrition = findViewById(R.id.enter_nutrition_et);
            String nutritionalInfo = etNutrition.getText().toString();

            Spinner spDiet = findViewById(R.id.diet_category_spinner);
            int diet = spDiet.getSelectedItemPosition();

            ImageView imageView = findViewById(R.id.recipe_picture_iv);

            FirestoreGC firebaseGC = FirestoreGC.getInstance();
            String pathToImage = null;
            Drawable drawable = imageView.getDrawable();
            //TODO: find some better condition to determine if the user has put a picture here.
            if(drawable != null && drawable instanceof BitmapDrawable) {
                pathToImage = firebaseGC.uploadRecipeImage(((BitmapDrawable) drawable).getBitmap(), firebaseGC.getOwnerID());
            }
            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setOwnerID(firebaseGC.getOwnerID());
            recipe.setPathToImage(pathToImage);
            recipe.setNutritionalInfo(nutritionalInfo);
            recipe.setIngredients(ingredientList);
            recipe.setSteps(stepsList);
            recipe.setDiet(diet);
            firebaseGC.addRecipe(recipe);

            Intent intent = new Intent(AddRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        });

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
        });


        //Dynamic Steps List
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
        });

        Button btnAddImage = findViewById(R.id.add_image_button);
        btnAddImage.setOnClickListener(v -> {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose your profile picture");
            builder.setItems(options, (dialog, item) -> {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, REQUEST_IMAGE_STORAGE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            });
            builder.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    updateUI();
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    try {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ivRecipe.setImageBitmap(selectedImage);
                    } catch (Exception e) {
                        Log.e(TAG, "Could not add photo (Camera).", e);
                    }
                    break;
                case REQUEST_IMAGE_STORAGE:
                    try {
                        final Uri imageUri = data.getData();
                        if(imageUri != null) {
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ivRecipe.setImageBitmap(selectedImage);
                        } else {
                            Log.e(TAG, "Image Uri == null", new NullPointerException());
                        }
                    } catch (FileNotFoundException e) {
                        Log.e(TAG, "Could not add photo (Gallery). ", e);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_add_menu, menu);
        //Log.d(TAG, "onCreateOptionsMenu: ");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_menu_setting: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            case R.id.action_main: {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
