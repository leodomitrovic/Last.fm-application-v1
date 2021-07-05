package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopTracksFragment1 extends Fragment implements LifecycleOwner, ViewModelStoreOwner {
    RecyclerView rv;
    private AdapterTracks at;
    private TopTracksViewModel model;

    public TopTracksFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_top_tracks, container, false);
        rv = view.findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        model = new ViewModelProvider(requireActivity()).get(TopTracksViewModel.class);
        model.init();
        final Observer<List<Track>> tracksObserver = new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                System.out.println("Observe");
                initRecyclerView(tracks);
                //bind.executePendingBindings();
            }
        };

        model.getTracks().observe(getViewLifecycleOwner(), tracksObserver);
        return view;
    }

    private void initRecyclerView(List<Track> tracks){
        at = new AdapterTracks(getActivity(), tracks);
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
    @Override
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
                State s = null;
                return s;
            }
        };
        return l;
    }
}