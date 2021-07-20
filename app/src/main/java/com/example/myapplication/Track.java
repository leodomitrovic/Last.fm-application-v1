package com.example.myapplication;

import android.net.Uri;

public class Track {
    public String name, listeners, playcount, artist;
    Uri icon;

    Track (String name, String listeners, Uri icon, String playcount, String artist) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
        this.artist = artist;
    }
}