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

public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.ViewHolder> {
    private final LayoutInflater layoutInflater;
    String[][] artists;
    ConstraintLayout root;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, listeners, playcount;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = view.findViewById(R.id.textView11);
            listeners = view.findViewById(R.id.textView12);
            playcount = view.findViewById(R.id.textView13);
            icon = view.findViewById(R.id.imageView2);
        }
    }

    AdapterInfo(Context context, ConstraintLayout root, String[][] artists) {
        layoutInflater = LayoutInflater.from(context);
        this.root = root;
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public AdapterInfo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_tracks, parent, false);
        return new AdapterInfo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInfo.ViewHolder holder, int position) {
        String[] artist = artists[position];
        holder.name.setText(artist[0]);
        holder.listeners.setText(artist[1]);
        holder.playcount.setText(artist[2]);
        Picasso.with(context).load(artist[3]).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ArtistDetail.class);
                i.putExtra("name", holder.name.getText());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.length;
    }
}
