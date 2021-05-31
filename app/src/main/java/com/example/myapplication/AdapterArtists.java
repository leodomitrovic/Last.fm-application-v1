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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AdapterArtists extends RecyclerView.Adapter<AdapterArtists.ViewHolder> {
    private final LayoutInflater layoutInflater;
    String[][] artists;
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

    AdapterArtists(Activity activity, String[][] artists) {
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
        String[] artist = artists[position];
        holder.name.setText(artist[0]);
        holder.listeners.setText(artist[1]);
        holder.playcount.setText(artist[2]);
        Picasso.with(activity.getApplicationContext()).load(artist[3]).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fManager = activity.getFragmentManager();
                Fragment f;
                f = new ArtistDetailFragment(activity, holder.name.getText().toString());
                fManager.beginTransaction().replace(R.id.container, f).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.length;
    }
}
