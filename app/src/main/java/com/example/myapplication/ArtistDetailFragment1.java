package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityArtistInfoBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArtistDetailFragment1 extends Fragment {
    RecyclerView rv;
    String artist_name;
    private AdapterTracks at;
    private ArtistDetailViewModel model;

    public ArtistDetailFragment1() {
        // Required empty public constructor
    }

    public void setArguments(Bundle bundle) {
        this.artist_name = bundle.getString("artist_name");
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
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        model = new ViewModelProvider(this::getViewModelStore).get(ArtistDetailViewModel.class);
        model.init(artist_name);

        final Observer<List<Track>> tracksObserver = new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                initRecyclerView(tracks);
            }
        };

        model.getTracks().observe(getViewLifecycleOwner(), tracksObserver);

        final Observer<Artist> dataObserver = new Observer<Artist>() {
            @Override
            public void onChanged(Artist artist) {
                binding.setArtis(artist);
                binding.executePendingBindings();
            }
        };

        model.getArtist().observe(getViewLifecycleOwner(), dataObserver);

        /*view.findViewById(R.id.constraintLayout2).invalidate();
        binding.setArtis(model.getArtist().getValue());*/
        return view;
    }

    private void initRecyclerView(List<Track> tracks){
        at = new AdapterTracks(getActivity(), tracks);
        rv.setAdapter(at);
    }
}