package com.ashleyam.touchnotev1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ViewSong extends ActionBarActivity {
    TextView songTitleTV;
    TextView songDateCreatedTV;
    RecyclerView songLinesRecyclerView;
    RecyclerView.Adapter songLinesAdapter;

    int songNumber;

    List<Song> songList = new ArrayList<Song>();
    List<String> songLines = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);

        Intent getSongIndex = getIntent();
        songNumber = getSongIndex.getIntExtra("SONG_NUMBER", 0);

        readObject();



        songTitleTV = (TextView) findViewById(R.id.edit_song_title_text);
        songDateCreatedTV = (TextView) findViewById(R.id.edit_song_date_text);
        songLinesRecyclerView = (RecyclerView) findViewById(R.id.song_lines_recycler_view);

        songTitleTV.setText(songList.get(songNumber).getSongTitle());
        songDateCreatedTV.setText(songList.get(songNumber).getSongDateCreated());
        songLines = songList.get(songNumber).getSongNotes();

        LinearLayoutManager songLineLayoutManager = new LinearLayoutManager(this);
        songLineLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        songLinesRecyclerView.setLayoutManager(songLineLayoutManager);

        songLinesAdapter = new EditSongAdapter(songLines);
        songLinesRecyclerView.setAdapter(songLinesAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_song, menu);
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

        if (id == R.id.edit_song) {
            Intent editSongIntent = new Intent(this, EditSong.class);
            editSongIntent.putExtra("SONG_INDEX", songNumber);
            editSongIntent.putExtra("PREVIOUS_ACTIVITY", "0");
            startActivity(editSongIntent);
            return true;
        }

        if (id == R.id.delete_song) {
            songList.remove(songNumber);
            try {
                writeObject(songList);
            }

            catch(IOException e) {
                Toast.makeText(this, "Delete Failed.", Toast.LENGTH_SHORT).show();
            }

            Intent backIntent = new Intent(this, MainActivity.class);
            startActivity(backIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean readObject() {
        try {
            FileInputStream fileInputStream = openFileInput("songs");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            songList = (List<Song>) objectInputStream.readObject();

            return true;
        }
        catch (IOException e) {
            Toast.makeText(this, "Failed to read song.", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch(ClassNotFoundException c) {
            Toast.makeText(this, "Failed to read song.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void writeObject(List<Song> songList) throws IOException  {
        FileOutputStream fileOutputStream = openFileOutput("songs", Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(songList);
        objectOutputStream.close();
    }
}
