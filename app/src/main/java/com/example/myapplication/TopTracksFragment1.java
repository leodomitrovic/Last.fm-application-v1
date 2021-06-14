package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public class TopTracksFragment1 extends Fragment {
    RecyclerView rv;
    Activity activity;
    private AdapterTracks at;
    private TopTracksViewModel model;
    ViewDataBinding vd;

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
        rv = view.findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        initRecyclerView();
        vd = DataBindingUtil.bind(view);
        vd.executePendingBindings();
        return view;
    }

    private void initRecyclerView(){
        model = new ViewModelProvider(this::getViewModelStore).get(TopTracksViewModel.class);
        model.init();
        at = new AdapterTracks(activity, model.getTracks().getValue());
        rv.setAdapter(at);
    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }
}