package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerviewItemTracksBinding;

import java.util.List;

public class AdapterTracks extends RecyclerView.Adapter<AdapterTracks.ViewHolder> {
    final LayoutInflater layoutInflater;
    List<Track> tracks;
    Activity context;
    RecyclerviewItemTracksBinding binding;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    AdapterTracks(Activity context, List<Track> tracks) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public AdapterTracks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_tracks, parent, false);
        binding = DataBindingUtil.bind(view);
        return new AdapterTracks.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTracks.ViewHolder holder, int position) {
        Track track = tracks.get(position);
        Glide.with(context.getApplicationContext()).load(track.icon).into(holder.icon);
        binding.setTrack(track);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}