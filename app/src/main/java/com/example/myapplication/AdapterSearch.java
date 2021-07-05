package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.RecyclerviewItemSearchBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    private final LayoutInflater layoutInflater;
    List<Artist> artists;
    Activity activity;
    RecyclerviewItemSearchBinding binding;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    AdapterSearch(Activity activity, List<Artist> tracks) {
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        this.activity = activity;
        this.artists = tracks;
    }

    @NonNull
    @Override
    public AdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_search, parent, false);
        binding = DataBindingUtil.bind(view);
        return new AdapterSearch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Picasso.with(activity.getApplicationContext()).load(artist.icon).into(holder.icon);
        binding.setArtist(artist);
        binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fManager = activity.getFragmentManager();
                Fragment f;
                f = new ArtistDetailFragment1();
                Bundle b = new Bundle();
                b.putString("artist_name", artist.name);
                f.setArguments(b);
                fManager.beginTransaction().replace(R.id.container, f).commit();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
