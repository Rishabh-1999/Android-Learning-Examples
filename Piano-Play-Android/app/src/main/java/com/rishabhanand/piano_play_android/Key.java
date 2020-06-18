package com.rishabhanand.piano_play_android;

import android.graphics.RectF;

public class Key {

    public int sound;
    public RectF rect;
    public boolean down;

    public Key(RectF rect, int sound) {
        this.sound = sound;
        this.rect = rect;
    }
}
