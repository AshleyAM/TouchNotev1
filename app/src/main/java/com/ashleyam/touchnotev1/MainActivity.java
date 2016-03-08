package com.ashleyam.touchnotev1;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    RecyclerView songListRecyclerView;
    RecyclerView.Adapter songListAdapter;
    List<Song> songzTest = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readObject();

        songListRecyclerView = (RecyclerView) findViewById(R.id.song_item_recycler_view2);
        songListRecyclerView.setHasFixedSize(true);

        LinearLayoutManager songListLayoutManager;
        songListLayoutManager = new LinearLayoutManager(this);
        songListLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songListRecyclerView.setLayoutManager(songListLayoutManager);


        songListAdapter = new SongListAdapter(songzTest);
        songListRecyclerView.setAdapter(songListAdapter);

        songListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        else if (id == R.id.action_create_song) {
            FragmentManager manager = getFragmentManager();
            CreateSongDialogFragment csdf = new CreateSongDialogFragment();
            csdf.show(manager, "CreateSong");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean readObject() {
        try {
            FileInputStream fileInputStream = openFileInput("songs");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            songzTest = (List<Song>) objectInputStream.readObject();
            return true;
        }
        catch (IOException e) {
            Toast.makeText(this, "Failed to read songs.", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch(ClassNotFoundException c) {
            Toast.makeText(this, "Failed to read songs.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
