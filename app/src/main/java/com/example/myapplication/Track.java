package com.example.myapplication;

public class Track {
    private String name, listeners, icon, playcount, artist;

    Track (String name, String listeners, String icon, String playcount, String artist) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
        this.artist = artist;
    }

    String getName() {
        return name;
    }

    String getListeners() {
        return listeners;
    }

    String getIcon() {
        return icon;
    }

    String getPlaycount() {
        return playcount;
    }

    String getArtist() {
        return artist;
    }
}
