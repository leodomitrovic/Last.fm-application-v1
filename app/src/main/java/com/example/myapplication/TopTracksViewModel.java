package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TopTracksViewModel extends ViewModel {
    MutableLiveData<List<Track>> tracks;
    private TracksRepository repo;

    public void init(){
        if(tracks != null){
            return;
        }
        repo = TracksRepository.getInstance();
        tracks = repo.setTracks();
    }

    public LiveData<List<Track>> getTracks(){
        return tracks;
    }
}