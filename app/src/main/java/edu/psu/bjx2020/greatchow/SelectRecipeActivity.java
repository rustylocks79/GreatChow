package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
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
    private String recipeName;
    private String id;
    private Recipe recipe;
    static final String NAME_KEY = "recipename";
    static final String ID_KEY = "recipeID";
    static final String DATE_KEY = "day";
    static final String MONTH_KEY = "month";
    static final String YEAR_KEY = "year";



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
        Toolbar toolbar = findViewById(R.id.select_add_toolbar);
        setSupportActionBar(toolbar);

        postAuth();

    }

    // invoked when the activity may be temporarily destroyed, save the instance state here

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME_KEY, recipeName);
        outState.putString(ID_KEY, id);
        outState.putInt(DATE_KEY, dayOfMonth);
        outState.putInt(MONTH_KEY,month);
        outState.putInt(YEAR_KEY,year);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
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
            recipeName = recipe.getName();
            new ConfirmDialog(recipeName, id, recipe, year, month, dayOfMonth).show(getSupportFragmentManager(),"confirmdialog");
            //dialog.show(getSupportFragmentManager(), "confirmDialog");
        });
    }


    @Override
    public void onPositiveClick(DialogFragment dialog, String name, String id, int year, int month, int dayOfWeek) {
        Log.d(TAG, "User accepted scheduling id=" + id + " on date=" + month + "/" + dayOfWeek + "/" + year);
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        ScheduledRecipe sr = new ScheduledRecipe();
        sr.setId(id);
        sr.setYear(year);
        sr.setMonth(month);
        sr.setDayOfMonth(dayOfWeek);
        sr.setOwnerID(firestoreGC.getOwnerID());
        sr.setName(recipeName);
        firestoreGC.addScheduledRecipe(sr);
        Intent intent = new Intent(SelectRecipeActivity.this, MealScheduleActivity.class);
        Toast.makeText(this, "The recipe is added to date.", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    @Override
    public void onNegativeClick(DialogFragment confirmDialog) {
        confirmDialog.dismiss();
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

