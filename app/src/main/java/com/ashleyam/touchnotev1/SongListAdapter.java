package com.ashleyam.touchnotev1;

/**
 * Created by Ashley on 22/04/15.
 */

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ashley on 19/04/15.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListViewHolder> {
    private List<Song> songs;

    public SongListAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public void onBindViewHolder(SongListViewHolder songListViewHolder, int i) {
        Song si = songs.get(i);

        songListViewHolder.vsongTitle.setText(si.songTitle);
        songListViewHolder.vsongDateCreated.setText(si.songDateCreated);
    }

    @Override
    public SongListAdapter.SongListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.song_item_layout, viewGroup, false);

        SongListViewHolder slvh = new SongListViewHolder(itemView);
        return slvh;
    }

    public static class SongListViewHolder extends RecyclerView.ViewHolder {
        TextView vsongTitle;
        TextView vsongDateCreated;
        CardView card;

        public View view;


        public SongListViewHolder(View v) {
            super(v);
            view = v;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent viewSongIntent = new Intent(view.getContext(), ViewSong.class);
                    viewSongIntent.putExtra("SONG_NUMBER", getPosition());
                    view.getContext().startActivity(viewSongIntent);
                }
            });

            vsongTitle = (TextView) v.findViewById(R.id.song_title_text_view);
            vsongDateCreated = (TextView) v.findViewById(R.id.song_date_created_text_view);
            card = (CardView) v.findViewById(R.id.song_item_card_view);
        }
    }
}