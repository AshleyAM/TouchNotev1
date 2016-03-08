package com.ashleyam.touchnotev1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

/**
 * Created by Ashley on 28/04/15.
 */
public class RestDialogFragment extends DialogFragment {
    Spinner restSpinner;
    int restValue;

    public interface RestDialogListener {
        void onFinishEditDialog(int restVal);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view2 = inflater.inflate(R.layout.add_rest_dialog_fragment, null);

        restSpinner = (Spinner) view2.findViewById(R.id.rest_value_spinner);

        ArrayAdapter<CharSequence> restSpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.rest_array, android.R.layout.simple_spinner_item);
        restSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restSpinner.setAdapter(restSpinnerAdapter);

        builder.setView(view2)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        restValue = restSpinner.getSelectedItemPosition();


                        RestDialogListener activity = (RestDialogListener) getActivity();
                        activity.onFinishEditDialog(restValue);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RestDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
