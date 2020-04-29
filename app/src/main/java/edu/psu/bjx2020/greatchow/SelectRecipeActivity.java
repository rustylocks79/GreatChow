package edu.psu.bjx2020.greatchow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import edu.psu.bjx2020.greatchow.db.ScheduledRecipe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectRecipeActivity extends AppCompatActivity implements ConfirmDialog.ConfirmDialogListener {
    private static final String TAG = "SelectRecipeActivity";
    SimpleDateFormat sdf;
    ScheduledRecipe sr;
    int day, month, year;
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

        LinearLayout selectRecipeList = findViewById(R.id.search_recipe_list_ll);
        firestoreGC.getAllRecipes(Recipe.VEGETARIAN, task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Recipe recipe = document.toObject(Recipe.class);
                    //sr = document.toObject(ScheduledRecipe.class);
                    Button button = new Button(SelectRecipeActivity.this);
                    button.setText(recipe.getName());
                    button.setOnClickListener(v -> {
                        // todo set values of sr
                        //sdf = (SimpleDateFormat) getIntent().getExtras().getSerializable("date");
                        //sr.setDayOfMonth();
                        day = getIntent().getExtras().getInt("day");
                        month = getIntent().getExtras().getInt("month");
                        year = getIntent().getExtras().getInt("year");

                        showConfirmDialog(document.getId(), day, month, year);
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

    private void showConfirmDialog(String id, int day, int month, int year) {
        //TODO pass parameters

        FirestoreGC.getInstance().getRecipeByID(id, task -> {
            if(task.isSuccessful()) {
                Recipe recipe = task.getResult().toObject(Recipe.class);
                ConfirmDialog dialog = new ConfirmDialog(id, recipe, day, month, year);
                dialog.show(getSupportFragmentManager(), "confirmDialog");
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    @Override
    public void onPositiveClick(DialogFragment dialog, String id) {
        // TODO add the recipe to db
//        CalendarView mCalendar = findViewById(R.id.calendarView);
//        TextView mdate = findViewById(R.id.calendarTextView);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
//        String selectedDate = sdf.format(new Date(mCalendar.getDate()));

        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        ScheduledRecipe sr = new ScheduledRecipe();
        sr.setDayOfMonth(day);
        sr.setMonth(month);
        sr.setYear(year);
        sr.setId(id);
        sr.setOwnerID(firestoreGC.getOwnerID());
        firestoreGC.addScheduledRecipe(sr);
    }

    @Override
    public void onNegativeClick(DialogFragment confirmDialog) {
        //dismissDialog();
    }


}

