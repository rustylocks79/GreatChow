package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.firestore.DocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;
import edu.psu.bjx2020.greatchow.db.ScheduledRecipe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class MealScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MealScheduleActivity";

    private TextView tvDate;
    private int year, month, dayOfMonth;

    //TODO: allow multiple locals
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_schedule);
        Log.d(TAG, "Creating Meal Schedule Activity");

        //toolbar
        Toolbar mcalToolbar = findViewById(R.id.calendarToolbar);
        setSupportActionBar(mcalToolbar);

        //get date in calendar
        CalendarView mCalendar = findViewById(R.id.calendarView);
        Calendar instance = GregorianCalendar.getInstance();
        instance.setTime(new Date(mCalendar.getDate()));
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
        dayOfMonth = instance.get(Calendar.DAY_OF_MONTH);

        // disable past date
        mCalendar.setMinDate(System.currentTimeMillis() - 1000);

        // get current date and future date
        tvDate = findViewById(R.id.calendarTextView);
        tvDate.setText("Selected Date: " + month + "/" + dayOfMonth + "/" + year);
        mCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            this.year = year;
            this.month = month + 1;
            this.dayOfMonth = dayOfMonth;
            Log.d(TAG, "date selected: " + this.month + "/" + this.dayOfMonth + "/" + this.year);
            tvDate.setText("Selected Date: " + this.month + "/" + this.dayOfMonth + "/" + this.year);
        });

        showScheduledRecipe();
    }

    private void showScheduledRecipe() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        // LinearLayout format
        LinearLayout list = findViewById(R.id.list);
        firestoreGC.getScheduledRecipes(queryDocumentSnapshots -> {
            for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()) {
                ScheduledRecipe scheduledRecipe = document.toObject(ScheduledRecipe.class);
                Button button = new Button(MealScheduleActivity.this);
                String dateText = scheduledRecipe.getMonth() + "/" + scheduledRecipe.getDayOfMonth() + "/" + scheduledRecipe.getYear();
                button.setText(dateText);
                button.setOnClickListener(v -> firestoreGC.getRecipeByID(scheduledRecipe.getId(), documentSnapshot -> {
                    Intent intent = new Intent(MealScheduleActivity.this, ViewRecipeActivity.class);
                    intent.putExtra("recipe", documentSnapshot.toObject(Recipe.class));
                    intent.putExtra("id", document.getId());
                    startActivity(intent);
                }));
                list.addView(button);
                Log.d(TAG, document.getId() + " => " + scheduledRecipe.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calendar_menu, menu);
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
            case R.id.action_schedule_meal_todate: {
                Intent selectActivity = new Intent(this,SelectRecipeActivity.class);
                selectActivity.putExtra("year", year);
                selectActivity.putExtra("month", month);
                selectActivity.putExtra("dayOfMonth", dayOfMonth);
                startActivity(selectActivity);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
