package com.ashleyam.touchnotev1;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ashley on 23/04/15.
 */
public class MusicTextView extends TextView {
    public MusicTextView(Context context) {
        super(context);
        setFont();
    }
    public MusicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }
    public MusicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "MusiQwik.ttf");
        setTypeface(font, Typeface.NORMAL);
    }
}
