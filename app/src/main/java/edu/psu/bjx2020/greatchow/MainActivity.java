package edu.psu.bjx2020.greatchow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "MainActivity";

    private Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //firestore
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        if (!firestoreGC.isAuthenticated()) {
            //TODO: disable anonymous authentication

//            List<AuthUI.IdpConfig> providers = Arrays.asList(
//                    new AuthUI.IdpConfig.EmailBuilder().build());
//            startActivityForResult(
//                    AuthUI.getInstance()
//                            .createSignInIntentBuilder()
//                            .setAvailableProviders(providers)
//                            .setIsSmartLockEnabled(false)
//                            .build(),
//                    RC_SIGN_IN);

            FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Authenticated successfully");
                    FirestoreGC.getInstance().onSuccessfulAuthentication();
                    postAuth();
                } else {
                    Log.e(TAG, "Authentication Failed");
                }
            });
        }
    }


//TODO: disable anonymous authentication

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);
//            if (resultCode == RESULT_OK) {
//                postAuth();
//            } else {
//                Log.e(TAG, "authentication failed");
//            }
//        }
//    }

    public void postAuth() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayout llRecipeList = findViewById(R.id.recipe_list_ll);
        firestoreGC.getAllRecipes(false, false, task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Recipe recipe = document.toObject(Recipe.class);
                    Button button = new Button(MainActivity.this);
                    button.setText(recipe.getName());
                    button.setOnClickListener(v -> {
                        Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                        intent.putExtra("recipe", recipe);
                        startActivity(intent);
                    });
                    llRecipeList.addView(button);
                    Log.d(TAG, document.getId() + " => " + recipe.toString());
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });

//        firestoreGC.addRecipes(
//                new Recipe("Scrambled Eggs", currentUser.getUid(), true, false),
//                new Recipe("Macaroni and Cheese", currentUser.getUid(), true, false));
//
//        //show all vegetarian recipes;
//        firestoreGC.getAllRecipes(true, false, task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Recipe recipe = document.toObject(Recipe.class);
//                    Log.d(TAG, document.getId() + " => " + recipe.toString());
//                }
//            } else {
//                Log.e(TAG, "Error getting documents: ", task.getException());
//            }
//        });
//
//        //update a recipe
//        firestoreGC.getRecipes("Scrambled Eggs", false, false, task -> {
//            if (task.isSuccessful()) {
//                Recipe recipe = task.getResult().getDocuments().get(0).toObject(Recipe.class);
//                recipe.setName("Bacon and Eggs");
//                firestoreGC.updateRecipe(task.getResult().getDocuments().get(0).getReference(), recipe);
//            } else {
//                Log.e(TAG, "Could not find scrambled eggs", task.getException());
//            }
//        });
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
                startActivity(new Intent(this,meal_schedule.class));
                return true;
            }
            default:
            return super.onOptionsItemSelected(item);
        }
    }


}
