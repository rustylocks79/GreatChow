package edu.psu.bjx2020.greatchow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import edu.psu.bjx2020.greatchow.db.Recipe;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConfirmDialog extends DialogFragment {
    private static final String TAG = "ConfirmDialog";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private String name;
    private String id;
    private Recipe recipe;
    private int year, month, dayOfMonth;
    static final String NAME_KEY = "name";
    static final String ID_KEY = "recipeID";
    static final String DATE_KEY = "day";
    static final String MONTH_KEY = "month";
    static final String YEAR_KEY = "year";

    private ConfirmDialogListener listener;

    public ConfirmDialog() {}

    public ConfirmDialog(String name, String id, Recipe recipe, int year, int month, int dayOfMonth) {
        this.name = name;
        this.id = id;
        this.recipe = recipe;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public interface ConfirmDialogListener{

        void onPositiveClick(DialogFragment dialog, String name, String id, int year, int month, int dayOfMonth);

        void onNegativeClick(DialogFragment confirmDialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String dateText ;

        if (savedInstanceState != null) {
            name = savedInstanceState.getString(NAME_KEY);
            id = savedInstanceState.getString(ID_KEY);
            year = savedInstanceState.getInt(YEAR_KEY);
            month = savedInstanceState.getInt(MONTH_KEY);
            dayOfMonth = savedInstanceState.getInt(DATE_KEY);
            dateText = month + "/" + dayOfMonth + "/" + year;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add recipe?")
                    .setMessage("Add " + name + " to your meal plan on " + dateText + "?")
                    .setPositiveButton("Accept", (dialog, which) -> listener.onPositiveClick(ConfirmDialog.this, name, id, year, month, dayOfMonth))
                    .setNegativeButton("Cancel", (dialog, id) -> listener.onNegativeClick(ConfirmDialog.this));

            return builder.create();
        }
        else {
            dateText = month + "/" + dayOfMonth + "/" + year;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            name = recipe.getName();
            builder.setTitle("Add recipe?")
                    .setMessage("Add " + name + " to your meal plan on " + dateText + "?")
                    .setPositiveButton("Accept", (dialog, which) -> listener.onPositiveClick(ConfirmDialog.this, name, id, year, month, dayOfMonth))
                    .setNegativeButton("Cancel", (dialog, id) -> listener.onNegativeClick(ConfirmDialog.this));

            return builder.create();
        }
    }
    


    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME_KEY, name);
        outState.putString(ID_KEY, id);
        outState.putInt(DATE_KEY, dayOfMonth);
        outState.putInt(MONTH_KEY,month);
        outState.putInt(YEAR_KEY,year);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    public void handleOK()
    {
        Toast.makeText(getActivity(), "The recipe is added to date.", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if (context instanceof ConfirmDialogListener) {
            listener = (ConfirmDialogListener) context;
        } else {
            throw new IllegalArgumentException(context+" must implement SetupDialogListener");
        }
    }
}
