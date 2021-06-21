package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class TopTracksViewModel extends ViewModel {
    private MutableLiveData<List<Track>> tracks;
    private TracksRepository repo;

    public void init(){
        if(tracks != null){
            return;
        }
        repo = TracksRepository.getInstance();
        tracks = repo.getTracks();
    }

    public LiveData<List<Track>> getTracks(){
        return tracks;
    }
}