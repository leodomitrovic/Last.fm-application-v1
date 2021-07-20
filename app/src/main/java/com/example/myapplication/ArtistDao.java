package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ArtistDao {
    @Query("SELECT * FROM artistentity")
    List<ArtistEntity> getAll();

    @Query("SELECT * FROM artistentity WHERE artist_name IN (:names)")
    List<ArtistEntity> loadAllByIds(int[] names);

    @Query("SELECT * FROM artistentity WHERE artist_name LIKE :first LIMIT 1")
    ArtistEntity findByName(String first);

    @Insert
    void insertAll(ArtistEntity... users);

    @Delete
    void delete(ArtistEntity user);
}
