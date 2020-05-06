package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class ViewRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private String id;
    private ImageView recipeIV;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");
        id = getIntent().getExtras().getString("id");
        recipeIV = findViewById(R.id.recipe_picture_iv);

        Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(view -> {
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                if (!(recipeIV.getDrawable() instanceof BitmapDrawable)) {
                    Toast.makeText(this, "No Image to share", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap image = ((BitmapDrawable) recipeIV.getDrawable()).getBitmap();
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(content);
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(recipe.getPathToImage() != null) {
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
            firestoreGC.deleteRecipe(id);
            Snackbar.make(view, "Deleted " + recipe.getName(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            Intent intent = new Intent(ViewRecipeActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
