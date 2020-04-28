package edu.psu.bjx2020.greatchow;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SettingsActivity";

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate (savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
    }


}
