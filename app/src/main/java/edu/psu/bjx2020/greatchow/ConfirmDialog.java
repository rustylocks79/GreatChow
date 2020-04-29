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
    Recipe mRecipe;
    TextView mdate ;
    String selectedDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd / MM / yyyy");
    CalendarView mCalendar ;
    Owner owner;

    ScheduledRecipe sr;

    private ConfirmDialogListener listener;
    public ConfirmDialog() {}
    public ConfirmDialog(ScheduledRecipe sr) {
        this.sr = sr;
    }


    public interface ConfirmDialogListener{
        public void onPositiveClick(DialogFragment confirmDialog);

        void onNegativeClick(DialogFragment confirmDialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String mdate = String.valueOf(sr.getDayOfMonth() + sr.getMonth() + sr.getYear());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add recipe?")
                .setMessage("Add "+ mRecipe + " to your " + mdate + "?")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //add the recipe to calendar
                        listener.onPositiveClick(ConfirmDialog.this);
                        handleOK();
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
