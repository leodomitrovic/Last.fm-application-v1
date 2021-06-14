package com.example.myapplication;

public class Artist {
    String name, listeners, playcount, urlv, tag, icon;

    Artist (String name, String listeners, String icon, String playcount) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
    }

    Artist (String name, String listeners, String icon, String playcount, String urlv, String tag) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.playcount = playcount;
        this.urlv = urlv;
        this.tag = tag;
    }

    Artist (String name, String listeners, String icon, String urlv, int a) {
        this.name = name;
        this.listeners = listeners;
        this.icon = icon;
        this.urlv = urlv;
    }
}
