package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import java.util.List;


public class SearchArtistsFragment1 extends Fragment {
    RecyclerView rv;
    private AdapterSearch aa;
    private SearchArtistsViewModel model;
    ImageView search;
    EditText e;

    public SearchArtistsFragment1() {
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
        View view = inflater.inflate(R.layout.activity_search_artists, container, false);
        rv = view.findViewById(R.id.rv2);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        search = view.findViewById(R.id.imageView4);
        e = view.findViewById(R.id.editTextTextPersonName2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model = new ViewModelProvider(getActivity()).get(SearchArtistsViewModel.class);
                model.init(e.getText().toString());
                final Observer<List<Artist>> tracksObserver = new Observer<List<Artist>>() {
                    @Override
                    public void onChanged(List<Artist> artists) {
                        System.out.println("Observe");
                        initRecyclerView(artists);
                        //bind.executePendingBindings();
                    }
                };

                model.getArtists().observe(getViewLifecycleOwner(), tracksObserver);
            }
        });
        return view;
    }

    private void initRecyclerView(List<Artist> artists){
        aa = new AdapterSearch(getActivity(), artists);
        rv.setAdapter(aa);
    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }
}