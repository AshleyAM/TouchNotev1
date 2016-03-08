package com.ashleyam.touchnotev1;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ashley on 23/04/15.
 */
public class Song implements Serializable {
    String songTitle;
    String songDateCreated;
    List<String> songNotes;
    double beatCount;
    int barCount;
    int lineCount;

    public Song(String title, String dateCreated, List<String> notes) {
        this.songTitle = title;
        this.songDateCreated = dateCreated;
        this.songNotes = notes;
    }

    public Song(String title, String dateCreated) {
        this.songTitle = title;
        this.songDateCreated = dateCreated;
    }

    public Song() {
    }

    public void setSongTitle(String title) {
        this.songTitle = title;
    }

    public void setSongDateCreated(String date) {
        this.songDateCreated = date;
    }

    public void setSongNotes(List<String> notes) {
        this.songNotes = notes;
    }

    public void setBeatCount(double beatCount) {
        this.beatCount = beatCount;
    }

    public void setBarCount(int barCount) { this.barCount = barCount; }

    public void setLineCount(int lineCount) { this.lineCount = lineCount; }

    public double getBeatCount() {
        return beatCount;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongDateCreated() {
        return songDateCreated;
    }

    public List<String> getSongNotes() {
        return songNotes;
    }

    public int getBarCount() {
        return barCount;
    }

    public int getLineCount() {
        return lineCount;
    }
}
