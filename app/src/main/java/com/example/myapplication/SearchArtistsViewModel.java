package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SearchArtistsViewModel extends ViewModel {
    private MutableLiveData<List<Artist>> artists;
    private SearchArtistsRepository repo;

    public void init(String name){
        repo = new SearchArtistsRepository();
        artists = repo.setArtists(name);
    }

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }
}
