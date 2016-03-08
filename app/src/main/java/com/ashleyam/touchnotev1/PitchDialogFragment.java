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
import android.widget.RadioButton;
import android.widget.Spinner;


/**
 * Created by Ashley on 23/04/15.
 */
public class PitchDialogFragment extends DialogFragment{
    Spinner pitchSpinner;
    RadioButton sharpRadioButton;
    RadioButton flatRadioButton;
    RadioButton naturalRadioButton;
    int dotValue;


    int pitchValue;
    int sharpFlat;


    public interface PitchDialogListener {
        void onFinishEditDialog(int pitchVal, int dotted);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view2 = inflater.inflate(R.layout.pitch_dialog_fragment, null);

        pitchSpinner = (Spinner) view2.findViewById(R.id.pitch_select_spinner);
        sharpRadioButton = (RadioButton) view2.findViewById(R.id.sharp_radio_button);
        flatRadioButton = (RadioButton) view2.findViewById(R.id.flat_radio_button);
        naturalRadioButton = (RadioButton) view2.findViewById(R.id.natural_radio_button);


        ArrayAdapter<CharSequence> pitchSpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.pitch_array, android.R.layout.simple_spinner_item);
        pitchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pitchSpinner.setAdapter(pitchSpinnerAdapter);

        builder.setView(view2)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        pitchValue = pitchSpinner.getSelectedItemPosition();
                        dotValue = 0;

                        PitchDialogListener activity = (PitchDialogListener) getActivity();
                        activity.onFinishEditDialog(pitchValue, dotValue);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PitchDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
