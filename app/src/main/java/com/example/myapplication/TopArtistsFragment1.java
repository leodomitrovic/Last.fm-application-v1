package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TopArtistsFragment1 extends Fragment {
    RecyclerView rv;
    Activity activity;
    private AdapterArtists aa;
    private TopArtistsViewModel model;

    public TopArtistsFragment1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public TopArtistsFragment1(Activity activity) {
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
        View view = inflater.inflate(R.layout.activity_top_artists, container, false);
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void initRecyclerView(){
        aa = new AdapterArtists(activity, model.getArtists().getValue());

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(activity.getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(aa);
    }
}