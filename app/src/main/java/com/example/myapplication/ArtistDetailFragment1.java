package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArtistDetailFragment1 extends Fragment {
    Activity activity;
    String artist_name;

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
        return inflater.inflate(R.layout.fragment_artist_detail1, container, false);
    }
}