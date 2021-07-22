package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class TopArtistsViewModel extends AndroidViewModel {
    MutableLiveData<List<Artist>> artists;
    private ArtistsRepository repo;

    public TopArtistsViewModel(@NonNull Application application) {
        super(application);
        repo = new ArtistsRepository(application);
    }

    public void init(){
        if(artists != null){
            return;
        }
        artists = repo.setArtists();
    }

    public LiveData<List<Artist>> getArtists(){
        return artists;
    }
}
