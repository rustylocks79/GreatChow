package edu.psu.bjx2020.greatchow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.psu.bjx2020.greatchow.db.*;
import android.view.Menu;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Needed to insure that database instance is created.
        //AppDatabase rdb = AppDatabase.getDatabase(getApplicationContext());

        //AppDatabase.insert(new Recipe(1, "Pancakes", "Breakfast"));
        //AppDatabase.insert(new Ingredient(1, "Butter"));
        //AppDatabase.insert(new Contains(1, 1, "2/3 cup"));
        //AppDatabase.insert(new Recipe(2, "Scrambled Eggs", "More Breakfast"));
        //AppDatabase.insert(new Recipe(3, "General Tso's Chicken", "Good but not Breakfast"));
        //AppDatabase.insert(new Account(1, "jmd6724", "secure"));

        //AppDatabase.getRecipe(1, recipe -> {
        //    Log.d("Database", recipe.recipe.title);
        //    Log.d("Database", recipe.ingredients.get(0).title);
        //});

        //AppDatabase.getAccount(1, account -> {
        //    Log.d("Database", account.username);
        //    Log.d("Database", account.password);
        //});
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        Log.d(WINDOW_SERVICE, "onCreateOptionsMenu: ");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu_setting) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
