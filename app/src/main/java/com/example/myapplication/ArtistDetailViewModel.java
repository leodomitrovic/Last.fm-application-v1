package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArtistDetailViewModel extends AndroidViewModel {
    private MutableLiveData<List<Track>> tracks;
    private MutableLiveData<Artist> artist;
    private ArtistDetailRepository repo;

    public ArtistDetailViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new ArtistDetailRepository(application);
    }

    public void init(String name){
        artist = repo.setData(name);
        tracks = repo.setTracks(name);
    }

    public LiveData<List<Track>> getTracks(){
        return tracks;
    }

    public LiveData<Artist> getArtist(){
        return artist;
    }
}
