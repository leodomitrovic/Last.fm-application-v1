package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ArtistDetailViewModel extends ViewModel {
    private MutableLiveData<List<Track>> tracks;
    private MutableLiveData<Artist> artist;
    private ArtistDetailRepository repo;

    public void init(String name){
        if(tracks != null && artist != null){
            return;
        }
        repo = ArtistDetailRepository.getInstance(name);
        artist = repo.getData();
        tracks = repo.getTracks();
    }

    public LiveData<List<Track>> getTracks(){
        return tracks;
    }

    public LiveData<Artist> getArtist(){
        return artist;
    }
}
