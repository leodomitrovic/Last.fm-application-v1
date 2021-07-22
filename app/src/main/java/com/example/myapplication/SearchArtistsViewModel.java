package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchArtistsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Artist>> artists;
    private SearchArtistsRepository repo;

    public SearchArtistsViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new SearchArtistsRepository(application);
    }

    public void init(String name){
        artists = repo.setArtists(name);
    }

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }
}
