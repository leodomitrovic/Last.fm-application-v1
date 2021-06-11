package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TopArtistsViewModel extends ViewModel {
    private MutableLiveData<List<Artist>> artists;
    private ArtistsRepository repo;

    public void init(){
        repo = new ArtistsRepository();
        artists = repo.getArtists();
    }

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }
}
