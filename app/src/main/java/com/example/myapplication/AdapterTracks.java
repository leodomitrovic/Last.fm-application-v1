package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AdapterTracks extends RecyclerView.Adapter<AdapterTracks.ViewHolder> {
    private final LayoutInflater layoutInflater;
    String[][] tracks;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, listeners, playcount, artist;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.textView11);
            listeners = view.findViewById(R.id.textView12);
            playcount = view.findViewById(R.id.textView13);
            icon = view.findViewById(R.id.imageView2);
            artist = view.findViewById(R.id.textView14);
        }
    }

    AdapterTracks(Context context, String[][] tracks) {
        layoutInflater = LayoutInflater.from(context);
        //this.root = root;
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public AdapterTracks.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_tracks, parent, false);
        return new AdapterTracks.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTracks.ViewHolder holder, int position) {
        String[] track_list = tracks[position];
        holder.name.setText(track_list[0]);
        holder.listeners.setText(track_list[1]);
        holder.playcount.setText(track_list[2]);
        holder.artist.setText(track_list[3]);
        Picasso.with(context).load(track_list[4]).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return tracks.length;
    }
}
