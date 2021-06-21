package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityArtistInfoBinding;

import org.jetbrains.annotations.NotNull;

public class ArtistDetailFragment1 extends Fragment {
    RecyclerView rv;
    Activity activity;
    String artist_name;
    private AdapterTracks at;
    private ArtistDetailViewModel model;

    public ArtistDetailFragment1() {
        // Required empty public constructor
    }

    public ArtistDetailFragment1(Activity activity, String artist_name) {
        this.activity = activity;
        this.artist_name = artist_name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_artist_info, container, false);
        ActivityArtistInfoBinding binding = DataBindingUtil.bind(view);
        rv = view.findViewById(R.id.rv6);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        model = new ViewModelProvider(this::getViewModelStore).get(ArtistDetailViewModel.class);
        model.init(artist_name);

        while (model.getTracks().getValue().size() < 50);
        binding.setArtis(model.getArtist().getValue());
        binding.executePendingBindings();
        view.findViewById(R.id.constraintLayout2).invalidate();
        binding.setArtis(model.getArtist().getValue());
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        at = new AdapterTracks(activity, model.getTracks().getValue());
        rv.setAdapter(at);
    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }
}