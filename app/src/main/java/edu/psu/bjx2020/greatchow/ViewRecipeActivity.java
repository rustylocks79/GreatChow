package edu.psu.bjx2020.greatchow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

public class ViewRecipeActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");

        FloatingActionButton fab = findViewById(R.id.view_fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, recipe.getName(), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        });
    }
}
