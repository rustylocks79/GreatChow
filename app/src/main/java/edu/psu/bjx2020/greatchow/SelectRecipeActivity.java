package edu.psu.bjx2020.greatchow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class SelectRecipeActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener {
    private static final String TAG = "SelectRecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //finish floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        postAuth();
    }

    public void confirmAdd(View view){
        DialogFragment dialogFragment = new ConfirmDialog();
        dialogFragment.show(getSupportFragmentManager(),"addDialog");
    }


    public void postAuth() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        LinearLayout selectRecipeList = findViewById(R.id.search_recipe_list_ll);
        firestoreGC.getAllRecipes(false, false, task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Recipe recipe = document.toObject(Recipe.class);
                    Button button = new Button(SelectRecipeActivity.this);
                    button.setText(recipe.getName());
                    button.setOnClickListener(v -> {
                        // show add confirm box if need
                        showConfirmDialog();


                        // add the recipe to calendar
                    });
                    selectRecipeList.addView(button);
                    Log.d(TAG, document.getId() + " => " + recipe.toString());
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    private void showConfirmDialog() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.show(getSupportFragmentManager(), "confirmDialog");
    }

    @Override
    public void onPositiveClick() {
        //add the recipe to db
    }
}

