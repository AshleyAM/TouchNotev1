package com.ashleyam.touchnotev1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Ashley on 22/04/15.
 */
public class CreateSongDialogFragment extends DialogFragment {
    EditText songTitle;
    String songTitleString;
    Spinner keySpinner;
    int keySignature;
    String newSong = "1";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view2 = inflater.inflate(R.layout.create_song_dialog_fragment, null);

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Create New Song");

        songTitle = (EditText) view2.findViewById(R.id.song_title_edit_text);
        keySpinner = (Spinner) view2.findViewById(R.id.key_spinner);

        ArrayAdapter<CharSequence> keySpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.key_array, android.R.layout.simple_spinner_item);
        keySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        keySpinner.setAdapter(keySpinnerAdapter);

        builder.setView(view2)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        songTitleString = songTitle.getText().toString();
                        keySignature = keySpinner.getSelectedItemPosition();

                        if (songTitleString.trim().equals("")) {
                            CreateSongDialogFragment.this.getDialog().cancel();
                            Toast.makeText(getActivity(), "Please enter a song title.", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(getActivity(), EditSong.class);
                            intent.putExtra("SONG_TITLE", songTitleString);
                            intent.putExtra("KEY_SIGNATURE", keySignature);
                            intent.putExtra("PREVIOUS_ACTIVITY", newSong);
                            startActivity(intent);
                        }
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateSongDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}