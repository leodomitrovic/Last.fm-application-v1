package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import android.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;


public class SearchArtistsFragment1 extends Fragment {
    RecyclerView rv;
    Activity activity;
    private AdapterSearch aa;
    private SearchArtistsViewModel model;
    ImageView search;
    EditText e;

    public SearchArtistsFragment1() {
        // Required empty public constructor
    }

    public SearchArtistsFragment1 (Activity activity) {
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
        View view = inflater.inflate(R.layout.activity_search_artists, container, false);
        rv = view.findViewById(R.id.rv2);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        search = view.findViewById(R.id.imageView4);
        e = view.findViewById(R.id.editTextTextPersonName2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView(e.getText().toString());
            }
        });
        return view;
    }

    private void initRecyclerView(String name){
        model = new ViewModelProvider(this::getViewModelStore).get(SearchArtistsViewModel.class);
        model.init(name);
        while (model.getArtists().getValue().size() == 0);
        aa = new AdapterSearch(activity, model.getArtists().getValue());
        rv.setAdapter(aa);
    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }
}