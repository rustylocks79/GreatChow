package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import edu.psu.bjx2020.greatchow.db.FirestoreGC;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class meal_schedule extends AppCompatActivity {
    private static final String TAG = "calendar";

    CalendarView mCalendar;
    public static TextView mdate;
    SimpleDateFormat sdf;

    int day,month, year;
    List<CalendarContract.EventDays> recipe_events = new ArrayList<CalendarContract.EventDays>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_schedule);
        Log.d(TAG, "meal schedule activity created");

        //toolbar
        Toolbar mcalToolbar = findViewById(R.id.calendarToolbar);
        setSupportActionBar(mcalToolbar);

        //get date in calendar
        mCalendar = findViewById(R.id.calendarView);
        mdate = findViewById(R.id.calendarTextView);
        sdf = new SimpleDateFormat("dd/mm/yy") ;
        String selectedDate = sdf.format(new Date(mCalendar.getDate()));
        List<String> temp =  Arrays.asList(selectedDate.split("/"));
        day = Integer.parseInt(temp.get(0));
        month = Integer.parseInt(temp.get(1));
        year = Integer.parseInt(temp.get(2));

        // disable past date
        mCalendar.setMinDate(System.currentTimeMillis() - 1000);

        // get current date and future date
        mdate.setText("Date is : " + selectedDate );
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int tyear, int tmonth, int dayOfMonth) {
                // TODO Auto-generated method stub
                Log.d(TAG, "Date selected");
                day = dayOfMonth;
                month =tmonth+1;
                year = tyear;
                mdate.setText("Date is : " + dayOfMonth +" / " + month + " / " + year);
            }
        });

        showScheduledRecipe();
    }

    private void showScheduledRecipe() {
        FirestoreGC firestoreGC = FirestoreGC.getInstance();
        // LinearLayout format
        LinearLayout list = findViewById(R.id.list);
        firestoreGC.getAllMyRecipes(task -> {
            if(task.isSuccessful()) {
                for(QueryDocumentSnapshot document:task.getResult()) {
                    Recipe recipe = (Recipe) task.getResult().toObjects(Recipe.class);
                    Button button = new Button(meal_schedule.this);
                    button.setText(recipe.getName());
                    button.setOnClickListener(v -> {
                        Intent intent = new Intent(meal_schedule.this, ViewRecipeActivity.class);
                        intent.putExtra("recipe", recipe);
                        intent.putExtra("id", document.getId());
                        startActivity(intent);
                    });
                    list.addView(button);
                    Log.d(TAG, document.getId() + " => " + recipe.toString());
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
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
                //pass  date to selectRecipeActivity
                Intent selectActivity = new Intent(this,SelectRecipeActivity.class);
                selectActivity.putExtra("day", day);
                selectActivity.putExtra("month", month);
                selectActivity.putExtra("year", year);
                startActivity(selectActivity);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
