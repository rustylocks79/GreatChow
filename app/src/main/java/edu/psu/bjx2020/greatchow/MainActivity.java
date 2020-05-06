package edu.psu.bjx2020.greatchow;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        // toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                updateUI();
            } else {
                Log.e(TAG, "authentication failed");
            }
        }
    }



    public void updateUI() {
        //Uncomment and run app to populate database
//        Initializer.addRecipes();
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        LinearLayout llRecipeList = findViewById(R.id.recipe_list_ll);
        llRecipeList.removeAllViews();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int diet = Integer.parseInt(preferences.getString("diet", "0"));
        Log.d(TAG, "getting recipes with diet level: " + diet);
        firestoreGC.getAllRecipes(diet, queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                Recipe recipe = document.toObject(Recipe.class);
                Button button = new Button(MainActivity.this);
                button.setText(recipe.getName());
                button.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                    intent.putExtra("id", document.getId());
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                });
                llRecipeList.addView(button);
                Log.d(TAG, document.getId() + " => " + recipe.toString());
            }
        });
    }


    // inflate menu
    @SuppressLint("StringFormatInvalid")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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
            case R.id.action_add_recipe: {
                startActivity(new Intent(this,AddRecipeActivity.class));
                return true;
            }
            case R.id.action_schedule: {
                startActivity(new Intent(this, MealScheduleActivity.class));
                Log.d(TAG, "meal_schedule: ");
                return true;
            }
            case R.id.action_menu_sign_out: {
                FirebaseAuth.getInstance().signOut();
                finishAffinity(); //TODO: wrong
            }
            default:
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "shared change detected on key: " + key);
        if(key.equals("diet")) {
            updateUI();
        }
    }
}
