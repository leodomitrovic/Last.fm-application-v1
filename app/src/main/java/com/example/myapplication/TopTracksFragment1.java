package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
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

import com.example.myapplication.databinding.ActivityTopTracksBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopTracksFragment1 extends Fragment implements LifecycleOwner {
    RecyclerView rv;
    Activity activity;
    private AdapterTracks at;
    private TopTracksViewModel model;
    ViewDataBinding vd;
    ActivityTopTracksBinding binding;

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
        return view;
    }

    private void initRecyclerView(){
        model = new ViewModelProvider(this::getViewModelStore).get(TopTracksViewModel.class);
        model.init();
        at = new AdapterTracks(activity, model.getTracks().getValue());
        rv.setAdapter(at);
        try {
            synchronized (this) {
                wait(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        model.getTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                if (at == null) {
                    at = new AdapterTracks(activity, tracks);
                    rv.setAdapter(at);
                    //binding.executePendingBindings();
                } else {
                    at.tracks = tracks;
                    at.notifyDataSetChanged();
                    //binding.executePendingBindings();
                }
            }
        });
    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }

    @NonNull
    @NotNull
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
                return null;
            }
        };
        return l;
    }
}