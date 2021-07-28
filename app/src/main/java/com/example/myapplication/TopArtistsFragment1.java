package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class TopArtistsFragment1 extends Fragment {
    RecyclerView rv;
    private AdapterArtists aa;
    private TopArtistsViewModel model;
    ProgressBar p;

    public TopArtistsFragment1() {
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
        View view = inflater.inflate(R.layout.activity_top_artists, container, false);
        rv = view.findViewById(R.id.rv);
        p = view.findViewById(R.id.progressBar3);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        model = new ViewModelProvider(requireActivity()).get(TopArtistsViewModel.class);
        model.init();

        final Observer<List<Artist>> tracksObserver = new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                if (artists.size() < 50) {
                    p.setProgress(artists.size());
                } else {
                    p.setVisibility(View.INVISIBLE);
                    if (aa != null) {
                        aa.artists = artists;
                        aa.notifyDataSetChanged();
                        return;
                    }
                    initRecyclerView(artists);
                }
            }
        };
        model.getArtists().observe(getViewLifecycleOwner(), tracksObserver);
        return view;
    }

    private void initRecyclerView(List<Artist> artists) {
        aa = new AdapterArtists(getActivity(), artists, getActivity().getSupportFragmentManager());
        rv.setAdapter(aa);
    }
}