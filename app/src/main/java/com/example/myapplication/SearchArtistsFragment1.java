package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class SearchArtistsFragment1 extends Fragment {
    RecyclerView rv;
    private AdapterSearch aa;
    private SearchArtistsViewModel model;
    ImageView search;
    EditText e;
    ProgressBar p;

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
        p = view.findViewById(R.id.progressBar2);
        e = view.findViewById(R.id.editTextTextPersonName2);
        model = new ViewModelProvider(getActivity()).get(SearchArtistsViewModel.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perform();
            }
        });

        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                perform();
                return true;
            }
        });

        return view;
    }

    private void perform() {
        p.setVisibility(View.VISIBLE);
        aa = null;
        rv.setAdapter(aa);
        model.init(e.getText().toString());
        final Observer<List<Artist>> tracksObserver = new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                if (artists.size() < 30) { //30
                    p.setProgress(artists.size());
                } else {
                    p.setVisibility(View.INVISIBLE);
                    initRecyclerView(artists);
                }

            }
        };

        model.getArtists().observe(getViewLifecycleOwner(), tracksObserver);
    }

    private void initRecyclerView(List<Artist> artists){
        aa = new AdapterSearch(getActivity(), artists);
        rv.setAdapter(aa);
    }
}