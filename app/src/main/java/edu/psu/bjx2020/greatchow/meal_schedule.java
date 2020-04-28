package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class meal_schedule extends AppCompatActivity {

    //CalendarView mCalendar = findViewById(R.id.calendarView);
    //TextView mdate = findViewById(R.id.calendarTextView);
    //List<CalendarContract.EventDays> recipe_events = new ArrayList<CalendarContract.EventDays>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_schedule);
        Log.i("meal_schedule", "on create");

        //toolbar
        Toolbar mcalToolbar = findViewById(R.id.calendarToolbar);
        setSupportActionBar(mcalToolbar);

       // mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

           /* @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                //mdate.setText("Date is : " + dayOfMonth +" / " + (month+1) + " / " + year);

            }
        });*/

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
                startActivity(new Intent(this,SelectRecipeActivity.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
