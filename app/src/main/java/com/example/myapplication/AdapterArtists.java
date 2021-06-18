package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterArtists extends RecyclerView.Adapter<AdapterArtists.ViewHolder> {
    final LayoutInflater layoutInflater;
    //String[][] artists;
    List<Artist> artists;
    Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, listeners, playcount;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.textView11);
            listeners = view.findViewById(R.id.textView12);
            playcount = view.findViewById(R.id.textView13);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    //AdapterArtists(Activity activity, String[][] artists) {
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArtists.ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.name.setText(artist.name);
        holder.listeners.setText(artist.listeners);
        holder.playcount.setText(artist.playcount);
        Picasso.with(activity.getApplicationContext()).load(artist.icon).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fManager = activity.getFragmentManager();
                Fragment f;
                f = new ArtistDetailFragment1(activity, holder.name.getText().toString());
                fManager.beginTransaction().replace(R.id.container, f).commit();
            }
        });
        holder.itemView.invalidate();
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
}
