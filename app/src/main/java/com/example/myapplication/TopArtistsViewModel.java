package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TopArtistsViewModel extends ViewModel {
    MutableLiveData<List<Artist>> artists;
    private ArtistsRepository repo;

    public void init(){
        if(artists != null){
            return;
        }
        repo = ArtistsRepository.getInstance();
        artists = repo.setArtists();
    }

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }
}
