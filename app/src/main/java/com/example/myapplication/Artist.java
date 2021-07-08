package com.example.myapplication;

import android.net.Uri;

public class Artist {
    public String name, listeners, playcount, urlv, tag;
    Uri icon;

    Artist (String name, String listeners, Uri icon, String playcount) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
    }

    Artist (String name, String listeners, Uri icon, String playcount, String urlv, String tag) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
        this.urlv = urlv;
        this.tag = tag;
    }

    Artist (String name, String listeners, Uri icon, String urlv, int a) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.urlv = urlv;
    }
}