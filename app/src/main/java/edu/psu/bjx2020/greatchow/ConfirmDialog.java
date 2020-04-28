package edu.psu.bjx2020.greatchow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;

public class ConfirmDialog extends DialogFragment {

    public interface ConfirmDialogListener{
        public void onPositiveClick();
    }
    private ConfirmDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add recipe?")
                .setMessage("Add this recipe to your calendar?")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //add the recipe to calendar
                        listener.onPositiveClick();
                        handleOK();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
