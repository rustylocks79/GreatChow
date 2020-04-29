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

    private String mRecipeId;
    private Recipe mRecipe;
    private int year, month, dayOfWeek;

    private ConfirmDialogListener listener;
    public ConfirmDialog() {}
    public ConfirmDialog(String id, Recipe recipe, int year, int month, int dayOfWeek) {
        this.mRecipeId = id;
        this.mRecipe = recipe;
        this.year = year;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
    }

    public interface ConfirmDialogListener{
        void onPositiveClick(DialogFragment confirmDialog, String recipeID, int year, int month, int dayOfWeek);
        void onNegativeClick(DialogFragment confirmDialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String dateText = month + "/" + dayOfWeek + "/" + year;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add recipe?")
                .setMessage("Add "+ mRecipe.getName() + " to your meal plan on " + dateText + "?")
                .setPositiveButton("Accept", (dialog, which) -> listener.onPositiveClick(ConfirmDialog.this, mRecipeId, year, month, dayOfWeek))
                .setNegativeButton("Cancel", (dialog, id) -> listener.onNegativeClick(ConfirmDialog.this));
        return builder.create();
    }

    public void handleOK()
    {
        Toast.makeText(getActivity(), "The recipe is added to date.", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (ConfirmDialogListener) context;
    }
}
