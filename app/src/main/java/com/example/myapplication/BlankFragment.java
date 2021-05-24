package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BlankFragment extends Fragment implements View.OnClickListener {
    Button topArtists, topTracks, search;
    Activity a;
    View view;

    public BlankFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        topArtists = view.findViewById(R.id.button2);
        topTracks = view.findViewById(R.id.button3);
        search = view.findViewById(R.id.button4);
        a = getActivity();
        topArtists.setOnClickListener(this);
        topTracks.setOnClickListener(this);
        search.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                startActivity(new Intent(a, TopArtists.class));
                break;
            case R.id.button3:
                startActivity(new Intent(a, TopTracks.class));
                break;
            case R.id.button4:
                startActivity(new Intent(a, SearchArtists.class));
                break;
        }
    }
}