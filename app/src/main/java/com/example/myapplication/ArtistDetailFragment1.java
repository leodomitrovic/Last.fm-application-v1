package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArtistDetailFragment1 extends Fragment {
    RecyclerView rv;
    TextView name, listeners, playcount, urlv, tag;
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
        name = view.findViewById(R.id.textView6);
        rv = view.findViewById(R.id.rv6);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        model = new ViewModelProvider(this::getViewModelStore).get(ArtistDetailViewModel.class);
        model.init(artist_name);

        model.getTracks().observe(this::getLifecycle, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                at.notifyDataSetChanged();
            }
        });

        model.getArtist().observe(this::getLifecycle, new Observer<Artist>() {
            @Override
            public void onChanged(Artist artist) {
                name.setText(artist.name);
                listeners.setText(artist.listeners);
                playcount.setText(artist.playcount);
                urlv.setText(artist.urlv);
                tag.setText(artist.tag);
            }
        });

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

    @NonNull
    @NotNull
    public Lifecycle getLifecycle() {
        Lifecycle l = new Lifecycle() {
            @Override
            public void addObserver(@NonNull @NotNull LifecycleObserver observer) {

            }

            @Override
            public void removeObserver(@NonNull @NotNull LifecycleObserver observer) {

            }

            @NonNull
            @NotNull
            @Override
            public State getCurrentState() {
                return null;
            }
        };
        return l;
    }
}