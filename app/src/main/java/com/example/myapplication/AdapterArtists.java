package com.example.myapplication;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.RecyclerviewItemArtistsBinding;

import java.util.List;

public class AdapterArtists extends RecyclerView.Adapter<AdapterArtists.ViewHolder> {
    final LayoutInflater layoutInflater;
    List<Artist> artists;
    Activity activity;
    RecyclerviewItemArtistsBinding binding;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    AdapterArtists(Activity activity, List<Artist> artists) {
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        this.activity = activity;
        this.artists = artists;
    }

    @NonNull
    @Override
    public AdapterArtists.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_artists, parent, false);
        binding = DataBindingUtil.bind(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtists.ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Glide.with(activity.getApplicationContext()).load(artist.icon).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fManager = activity.getFragmentManager();
                Fragment f;
                f = new ArtistDetailFragment1(activity, artist.name);
                fManager.beginTransaction().replace(R.id.container, f).commit();
            }
        });
        binding.setVariable(BR.artist, artist);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}