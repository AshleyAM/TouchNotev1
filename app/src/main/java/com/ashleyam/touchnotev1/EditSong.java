package com.ashleyam.touchnotev1;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditSong extends ActionBarActivity implements PitchDialogFragment.PitchDialogListener, RestDialogFragment.RestDialogListener {
    String songTitleTVString;
    TextView songTitleTV;
    TextView songDateCreatedTV;
    String newSong = "0";
    int songNumber;

    int key; int sharpFlat; int pitchVal2; int noteVal; double noteCount; int dotVal; int lineCount = 0;
    int barCount; String currentNote; double beatCount = 0; int restValue;

    String [] keyArray = {"","¡", "¢", "£", "¤", "¥", "¦", "§", "¨", "©", "ª", "«", "¬", "€", "®"};

    String [][] notesArray = {
            {"@", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"},
            {"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "[", "\\", "]", "^"},
            {"`", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"},
            {"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "{", "|", "}", "~"}
    };

    int [] noteSpacingArray = {3, 6, 12, 24};

    String [] dotArray = {"°", "±", "²", "³", "´", "µ", "¶", "·", "¸", "¹", "º", "»", "¼", "½", "¾"};

    String [][] sharpFlatNaturalArray = {
            {"à", "á", "â", "ã", "ä", "å", "æ", "ç", "è", "é", "ê", "ë", "ì", "í", "î"},
            {"Ð", "Ñ", "Ò", "Ó", "Ô", "Õ", "Ö", "×", "Ø", "Ù", "Ú", "Û", "Ü", "Ý", "Þ"},
            {"ð", "ñ", "ò", "ó", "ô", "õ", "ö", "÷", "ø", "ù", "ú", "û", "ü", "ý", "þ"}
    };

    String [] restArray = {"9", ":", ";", "<"} ;

    double [] noteValueArray = {0.5, 1, 2, 4};

    RecyclerView songLinesRecyclerView;
    RecyclerView.Adapter songLinesAdapter;
    List<String> songLines = new ArrayList<String>();
    List<Song> existingSongList = new ArrayList<Song>();
    List<Song> songList = new ArrayList<Song>();

    int maxPointercount;
    int previousPointercount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);
        setTitle("Edit Song");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readObject();

        songTitleTV = (TextView) findViewById(R.id.edit_song_title_text);
        songDateCreatedTV = (TextView) findViewById(R.id.edit_song_date_text);
        songLinesRecyclerView = (RecyclerView) findViewById(R.id.song_lines_recycler_view);

        LinearLayoutManager songLineLayoutManager = new LinearLayoutManager(this);
        songLineLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songLinesRecyclerView.setLayoutManager(songLineLayoutManager);

        Intent getSongInfo = getIntent();
        newSong = getSongInfo.getStringExtra("PREVIOUS_ACTIVITY");

        if (newSong.equals("1")) {
            songTitleTVString = getSongInfo.getStringExtra("SONG_TITLE");

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            songDateCreatedTV.setText(sdf.format(c.getTime()));

            key = getSongInfo.getIntExtra("KEY_SIGNATURE", 0);
            songTitleTV.setText(songTitleTVString);
            songLines.add("'&=" + keyArray[key] + "=4");
        }

        else {
            songNumber = getSongInfo.getIntExtra("SONG_INDEX", 0);

            songTitleTV.setText(existingSongList.get(songNumber).getSongTitle());
            songDateCreatedTV.setText(existingSongList.get(songNumber).getSongDateCreated());
            songLines = existingSongList.get(songNumber).getSongNotes();
            beatCount = existingSongList.get(songNumber).getBeatCount();
            barCount = existingSongList.get(songNumber).getBarCount();
            lineCount = existingSongList.get(songNumber).getLineCount();
        }

        songLinesAdapter = new EditSongAdapter(songLines);
        songLinesRecyclerView.setAdapter(songLinesAdapter);
    }
/* --------------------------- MULTITOUCH & DIALOG ------------------------- */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int currentpointerCount = event.getPointerCount();
        final int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (maxPointercount <= previousPointercount) {
                    maxPointercount = currentpointerCount;
                }
                previousPointercount = currentpointerCount;
        }

        if (action == MotionEvent.ACTION_UP) {
            if (maxPointercount == 2) {
                noteVal = 0;
                clearModifiers();
                showPitchDialog();

            } else if (maxPointercount == 3) {
                noteVal = 1;
                clearModifiers();
                showPitchDialog();

            } else if (maxPointercount == 4) {
                noteVal = 2;
                clearModifiers();
                showPitchDialog();
            }

            else if (maxPointercount == 5) {
                noteVal = 3;
                clearModifiers();
                showPitchDialog();
            }

            maxPointercount = 0;
            previousPointercount = 0;
        }


        if(event.getAction() == MotionEvent.ACTION_DOWN)
            handler.postDelayed(mLongPressed, 700);
        if((event.getAction() == MotionEvent.ACTION_MOVE)||(event.getAction() == MotionEvent.ACTION_UP))
            handler.removeCallbacks(mLongPressed);
        return true;
    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            showRestDialog();
        }
    };


    public void clearModifiers() {
        dotVal = 0 ;
        sharpFlat = 3;
    }

    public boolean showPitchDialog(){
        FragmentManager manager2 = getFragmentManager();
        PitchDialogFragment pdf = new PitchDialogFragment();
        pdf.show(manager2, "ChosePitch");
        return true;
    }

    public boolean showRestDialog() {
        FragmentManager manager3 = getFragmentManager();
        RestDialogFragment rdf = new RestDialogFragment();
        rdf.show(manager3, "ChoseRest");
        return true;
    }

    // ------------------------------------------NOTE DRAWER-------------------------------------------------

    public void writeNote() {
        if (4-beatCount > noteValueArray[noteVal] || dotVal == 1 && 4-beatCount > noteValueArray[noteVal]+noteValueArray[noteVal]/2) {
            currentNote = notesArray[noteVal][pitchVal2];

            if (sharpFlat == 0 || sharpFlat == 1) {
                currentNote = sharpFlatNaturalArray[sharpFlat][pitchVal2] + currentNote;
            } else {
                currentNote = "=" + currentNote;
            }

            if (dotVal == 1) {
                currentNote = currentNote + dotArray[pitchVal2];
                beatCount = beatCount + (noteValueArray[noteVal]+(noteValueArray[noteVal]/2));
            } else {
                beatCount = beatCount + noteValueArray[noteVal];
            }

            while (currentNote.length() < noteSpacingArray[noteVal]) {
                currentNote = currentNote + "=";
            }

            songLines.set(lineCount, songLines.get(lineCount) + currentNote);
        }

        else if (4-beatCount == noteValueArray[noteVal] || dotVal == 1 && 4-beatCount == noteValueArray[noteVal]+noteValueArray[noteVal]/2)  {
            currentNote = notesArray[noteVal][pitchVal2];

            if (sharpFlat == 0 || sharpFlat == 1) {
                currentNote = sharpFlatNaturalArray[sharpFlat][pitchVal2] + currentNote;
            } else {
                currentNote = "=" + currentNote;
            }

            if (dotVal == 1) {
                currentNote = currentNote + dotArray[pitchVal2];
                beatCount = beatCount + (noteValueArray[noteVal]+(noteValueArray[noteVal]/2));
            } else {
                beatCount = beatCount + noteValueArray[noteVal];
            }

            while (currentNote.length() < noteSpacingArray[noteVal]) {
                currentNote = currentNote + "=";
            }

            barCount = barCount + 1;
            songLines.set(lineCount, songLines.get(lineCount) + currentNote);

            if (barCount != 4) {
                songLines.set(lineCount, songLines.get(lineCount) + "!=");
            }

            else {
                songLines.set(lineCount, songLines.get(lineCount) + "!");
                songLines.add("'&=" + keyArray[key] + "===");
                lineCount = lineCount + 1;
                barCount = 0;
            }
            beatCount = 0;
        }

        else {
            Toast.makeText(this, "This note cannot fit in the bar", Toast.LENGTH_LONG).show();
        }
        songLinesAdapter.notifyDataSetChanged();
    }

    public void writeRest() {
        if (4-beatCount > noteValueArray[restValue]) {
            currentNote = restArray[restValue];
            beatCount = beatCount + noteValueArray[restValue];

            currentNote = "=" + currentNote;

            while (currentNote.length() < noteSpacingArray[restValue]) {
                currentNote = currentNote + "=";
            }

            songLines.set(lineCount, songLines.get(lineCount) + currentNote);
        }

        else if (4-beatCount == noteValueArray[restValue]) {
            currentNote = restArray[restValue];
            barCount = barCount + 1;

            currentNote = "=" + currentNote;

            while (currentNote.length() < noteSpacingArray[restValue]) {
                currentNote = currentNote + "=";
            }

            songLines.set(lineCount, songLines.get(lineCount) + currentNote);

            if (barCount != 4) {
                songLines.set(lineCount, songLines.get(lineCount) + "!=");
            }

            else {
                songLines.set(lineCount, songLines.get(lineCount) + "!");
                songLines.add("'&=" + keyArray[key] + "=4");
                lineCount = lineCount + 1;
                barCount = 0;
            }
            beatCount = 0;
        }

        else {
            Toast.makeText(this, "This note cannot fit in the bar", Toast.LENGTH_LONG).show();
        }
        songLinesAdapter.notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_song, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            Intent startHelpIntent = new Intent(this, Help.class);
            startActivity(startHelpIntent);

            return true;
        }

        else if (id == R.id.home) {
            onBackPressed();
            return true;
        }

        else if (id == R.id.save_song) {
            try {
                Song song1 = new Song();
                song1.setSongNotes(songLines);
                song1.setSongTitle(songTitleTV.getText().toString());
                song1.setSongDateCreated(songDateCreatedTV.getText().toString());
                song1.setBeatCount(beatCount);
                song1.setBarCount(barCount);
                song1.setLineCount(lineCount);

                if (newSong.equals("1")) {
                    existingSongList.add(song1);
                    newSong = "0";
                }

                else {
                    existingSongList.set(songNumber, song1);
                }

                writeObject(existingSongList);
            } catch(IOException e) {
                Toast.makeText(this, "Saved Failed.", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "Song has been saved!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
// -------------------------------------------- EXTRAS ---------------------------------------------

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.flat_radio_button:
                if (checked)
                    sharpFlat = 0;
                break;
            case R.id.sharp_radio_button:
                if (checked)
                    sharpFlat = 1;
                break;
            case R.id.natural_radio_button:
                if (checked)
                    sharpFlat = 2;
                break;
        }
    }

    public void writeObject(List<Song> songList) throws IOException  {
        FileOutputStream fileOutputStream = openFileOutput("songs", Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(songList);
        objectOutputStream.close();
    }

    public boolean readObject() {
        try {
            FileInputStream fileInputStream = openFileInput("songs");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            existingSongList = (List<Song>) objectInputStream.readObject();

            return true;
        }
        catch (IOException e) {
            Toast.makeText(this, "Failed to read files.", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch(ClassNotFoundException c) {
            Toast.makeText(this, "Failed to read files.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onFinishEditDialog(int pitchVal, int dotted)
    {
        pitchVal2 = pitchVal;
        dotVal = dotted;
        writeNote();
    }

    @Override
    public void onFinishEditDialog(int restVal)
    {
        restValue = restVal;
        writeRest();
    }
}
