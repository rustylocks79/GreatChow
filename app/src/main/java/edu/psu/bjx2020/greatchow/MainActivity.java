package edu.psu.bjx2020.greatchow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "MainActivity";

    private Button calendar;
    private Button addTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // calendar
        calendar = (Button) findViewById(R.id.calendar);
        Intent incoming = getIntent();

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, meal_schedule.class);
                startActivity(intent);
            }

        });

        // ADD TEST
        addTest = findViewById(R.id.add_recipe_test);
        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }

        });

        // VIEW TEST
        addTest = findViewById(R.id.view_recipe_test);
        addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                startActivity(intent);
            }

        });


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
//        FirestoreGC firestoreGC = FirestoreGC.getInstance();
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
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
            default:
            return super.onOptionsItemSelected(item);
        }
    }


}
