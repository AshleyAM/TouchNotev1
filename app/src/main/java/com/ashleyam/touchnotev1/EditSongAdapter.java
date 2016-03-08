package com.ashleyam.touchnotev1;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ashley on 23/04/15.
 */
public class EditSongAdapter extends RecyclerView.Adapter<EditSongAdapter.EditSongViewHolder> {
    private List<String> songLines;

    public EditSongAdapter(List<String> songLines) {
        this.songLines = songLines;
    }

    @Override
    public int getItemCount() {
        return songLines.size();

    }

    @Override
    public void onBindViewHolder(EditSongViewHolder editSongViewHolder, int i) {
        String songLine = songLines.get(i);

        editSongViewHolder.score.setText(songLine);
    }

    @Override
    public EditSongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.edit_song_row, viewGroup, false);

        return new EditSongViewHolder(itemView);
    }

    public static class EditSongViewHolder extends RecyclerView.ViewHolder {
        MusicTextView score;


        public EditSongViewHolder(View v) {
            super(v);

            score = (MusicTextView) v.findViewById(R.id.stave_text_view);


        }
    }
}
