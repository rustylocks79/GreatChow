package edu.psu.bjx2020.greatchow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import edu.psu.bjx2020.greatchow.db.Recipe;
import edu.psu.bjx2020.greatchow.db.ScheduledRecipe;

import java.security.acl.Owner;
import java.text.SimpleDateFormat;

public class ConfirmDialog extends DialogFragment {
    String mRecipeId;
    Recipe mRecipe;
    TextView mdate;
    String selectedDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
    CalendarView mCalendar ;
    Owner owner;

    int dayOfMonth, month, year;

    private ConfirmDialogListener listener;
    public ConfirmDialog() {}
    public ConfirmDialog(String id, Recipe recipe, int dayOfMonth, int month, int year) {
        this.mRecipeId = id;
        this.mRecipe = recipe;
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }


    public interface ConfirmDialogListener{
        void onPositiveClick(DialogFragment confirmDialog, String id);
        void onNegativeClick(DialogFragment confirmDialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String mdate = dayOfMonth + "/" + month + "/" + year;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add recipe?")
                .setMessage("Add "+ mRecipe.getName()
                        + " to your " + mdate + "?")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPositiveClick(ConfirmDialog.this, mRecipeId);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onNegativeClick(ConfirmDialog.this);
                    }
                });
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
