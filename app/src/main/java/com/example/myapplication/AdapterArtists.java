package com.example.myapplication;

import android.app.Activity;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
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
    FragmentManager fm;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    AdapterArtists(Activity activity, List<Artist> artists, FragmentManager fm) {
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        this.activity = activity;
        this.artists = artists;
        this.fm = fm;
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
        binding.setArtist(artist);
        binding.executePendingBindings();
        Glide.with(activity.getApplicationContext()).load(artist.icon).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, ArtistDetailActivity.class).putExtra("name", artist.name));
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}