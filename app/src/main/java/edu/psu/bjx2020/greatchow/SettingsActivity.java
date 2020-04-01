package edu.psu.bjx2020.greatchow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences myPreference;
    private SharedPreferences.Editor editor;

    private CheckBox vege_checkbox;
    private List lang_list;
    private List sort_list;

    String[] languageArray;
    String[] sortArray;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lang_list = (List)findViewById(R.id.lList);
        sort_list = (List)findViewById(R.id.sList);
        vege_checkbox = (CheckBox)findViewById(R.id.vCheckBox);


        myPreference = PreferenceManager.getDefaultSharedPreferences(this);
        editor = myPreference.edit();

    }

    private void checkSharedPreferences()
    {
        languageArray = getResources().getStringArray(R.array.languageAlias);
        sortArray = getResources().getStringArray(R.array.sortingAttributes);

        String langlist = myPreference.getString(getString(R.string.list1), "en-US");
        String sortlist = myPreference.getString(getString(R.string.list2), "Alp");
        String vegecheckbox = myPreference.getString(getString(R.string.checkbox), "false");

        int p1 = Arrays.binarySearch(languageArray, langlist);
        int p2 = Arrays.binarySearch(sortArray, sortlist);

        lang_list.set(p1,this);
        sort_list.set(p2,this);

        if(vegecheckbox.equals("true")){
            vege_checkbox.setChecked(true);
        }
        else{
            vege_checkbox.setChecked(false);
        }
    }

    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate (savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
