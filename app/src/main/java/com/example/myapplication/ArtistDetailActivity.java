package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityArtistInfoBinding;

import java.util.List;

public class ArtistDetailActivity extends AppCompatActivity {
    RecyclerView rv;
    String artist_name;
    private AdapterTracks at;
    private ArtistDetailViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);
        artist_name = getIntent().getStringExtra("name");
        System.out.println(artist_name);
        ActivityArtistInfoBinding binding = DataBindingUtil.bind(findViewById(R.id.root5));
        rv = findViewById(R.id.rv6);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        model = new ViewModelProvider(this::getViewModelStore).get(ArtistDetailViewModel.class);
        model.init(artist_name);

        final Observer<List<Track>> tracksObserver = new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                initRecyclerView(tracks);
            }
        };

        final Observer<Artist> dataObserver = new Observer<Artist>() {
            @Override
            public void onChanged(Artist artist) {
                binding.setArtis(artist);
                binding.executePendingBindings();
            }
        };

        model.getTracks().observe(this, tracksObserver);
        model.getArtist().observe(this, dataObserver);
    }

    private void initRecyclerView(List<Track> tracks){
        at = new AdapterTracks(this, tracks);
        rv.setAdapter(at);
    }
}