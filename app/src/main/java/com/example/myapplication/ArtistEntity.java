package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ArtistEntity {
    @PrimaryKey
    public @NonNull String artist_name;

    @ColumnInfo(name = "image")
    public String image;
}
