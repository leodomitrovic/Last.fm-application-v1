package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ArtistEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArtistDao artistDao();
}
