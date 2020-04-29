package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;
import edu.psu.bjx2020.greatchow.db.ScheduledRecipe;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SelectRecipeActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener {
    private static final String TAG = "SelectRecipeActivity";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    private int year, month, dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);
        Bundle extras = getIntent().getExtras();
        year = extras.getInt("year");
        month = extras.getInt("month");
        dayOfMonth = extras.getInt("dayOfMonth");
        Log.d(TAG, "Creating Select Recipe Activity with date=" + month + "/" + dayOfMonth + "/" + year);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //finish floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        postAuth();
    }

    public void postAuth() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        LinearLayout selectRecipeList = findViewById(R.id.search_recipe_list_ll);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int diet = Integer.parseInt(preferences.getString("diet", "0"));
        //TODO: I do not account for shared preference changes during this activity. Going back to it after created will cause issues.
        firestoreGC.getAllRecipes(diet, queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                Recipe recipe = document.toObject(Recipe.class);
                Button button = new Button(SelectRecipeActivity.this);
                button.setText(recipe.getName());
                button.setOnClickListener(v -> {
                    showConfirmDialog(document.getId());
                });
                selectRecipeList.addView(button);
                Log.d(TAG, document.getId() + " => " + recipe.toString());
            }
        });
    }

    private void showConfirmDialog(String id) {
        FirestoreGC.getInstance().getRecipeByID(id, documentSnapshot -> {
            Recipe recipe = documentSnapshot.toObject(Recipe.class);
            ConfirmDialog dialog = new ConfirmDialog(id, recipe, year, month, dayOfMonth);
            dialog.show(getSupportFragmentManager(), "confirmDialog");
        });
    }

    @Override
    public void onPositiveClick(DialogFragment dialog, String id, int year, int month, int dayOfWeek) {
        Log.d(TAG, "User accepted scheduling id=" + id + " on date=" + month + "/" + dayOfWeek + "/" + year);
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        ScheduledRecipe sr = new ScheduledRecipe();
        sr.setId(id);
        sr.setYear(year);
        sr.setMonth(month);
        sr.setDayOfMonth(dayOfWeek);
        sr.setOwnerID(firestoreGC.getOwnerID());
        firestoreGC.addScheduledRecipe(sr);
        Intent intent = new Intent(SelectRecipeActivity.this, MealScheduleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNegativeClick(DialogFragment confirmDialog) {
        confirmDialog.dismiss();
    }


}

