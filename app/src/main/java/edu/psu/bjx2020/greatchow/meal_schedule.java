package edu.psu.bjx2020.greatchow;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class meal_schedule extends AppCompatActivity {
    private static final String TAG = "calendar";

    CalendarView mCalendar;
    TextView mdate;
    List<CalendarContract.EventDays> recipe_events = new ArrayList<CalendarContract.EventDays>();

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
        //mCalendar.getDate()
        //mdate.setText((int) mCalendar.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
        String selectedDate = sdf.format(new Date(mCalendar.getDate()));
        mdate.setText("Date is : " + selectedDate );
        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                Log.d(TAG, "Date selected");
                //selectedDate[0] = sdf.format(new Date(mCalendar.getDate()));
                //mdate.setText(selectedDate[0]);
                mdate.setText("Date is : " + dayOfMonth +" / " + (month+1) + " / " + year);
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

                startActivity(new Intent(this,SelectRecipeActivity.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
