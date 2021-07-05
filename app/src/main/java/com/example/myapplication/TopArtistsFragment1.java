package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

import com.example.myapplication.databinding.ActivityTopArtistsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class TopArtistsFragment1 extends Fragment implements LifecycleOwner, ViewModelStoreOwner {
    RecyclerView rv;
    private AdapterArtists aa;
    private TopArtistsViewModel model;

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
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ActivityTopArtistsBinding bind = DataBindingUtil.bind(view);
        model = new ViewModelProvider(getActivity()).get(TopArtistsViewModel.class);
        model.init();

        final Observer<List<Artist>> tracksObserver = new Observer<List<Artist>>() {
            @Override
            public void onChanged(List<Artist> artists) {
                System.out.println("Observe");
                initRecyclerView(artists);
                //bind.executePendingBindings();
            }
        };

        model.getArtists().observe(getViewLifecycleOwner(), tracksObserver);
        return view;
    }

    private void initRecyclerView(List<Artist> artists) {
        aa = new AdapterArtists(getActivity(), artists);
        rv.setAdapter(aa);

    }

    @NonNull
    @NotNull
    public ViewModelStore getViewModelStore() {
        ViewModelStore store = new ViewModelStore();
        return store;
    }

    @NonNull
    @NotNull
    @Override
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
                State s = null;
                return s;
            }
        };
        return l;
    }
}