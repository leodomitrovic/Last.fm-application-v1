package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterArtists extends RecyclerView.Adapter<AdapterArtists.ViewHolder> {
    private final LayoutInflater layoutInflater;
    List<String[]> artists;
    ConstraintLayout root;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, listeners, playcount;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = view.findViewById(R.id.textView);
            listeners = view.findViewById(R.id.textView1);
            playcount = view.findViewById(R.id.textView2);
            icon = view.findViewById(R.id.imageView2);
        }

        public boolean onLongClick(View v) {
            return true;
        }
    }

    AdapterArtists(Context context, ConstraintLayout root, List artists) {
        layoutInflater = LayoutInflater.from(context);
        this.root = root;
        this.context = context;
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
        String[] artist = artists.get(position);
        holder.name.setText(artist[0]);
        holder.listeners.setText(artist[1]);
        holder.playcount.setText(artist[2]);
        Picasso.with(context).load(artist[3]).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
