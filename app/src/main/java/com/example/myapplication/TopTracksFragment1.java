package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopTracksFragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopTracksFragment1 extends Fragment {
    RecyclerView rv;
    Activity activity;
    private AdapterTracks at;
    private TopTracksViewModel model;

    public TopTracksFragment1() {
        // Required empty public constructor
    }

    public TopTracksFragment1(Activity activity) {
        this.activity = activity;
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
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}