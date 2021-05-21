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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    private final LayoutInflater layoutInflater;
    String[][] artists;
    ConstraintLayout root;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, url, listeners;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = view.findViewById(R.id.textView11);
            icon = view.findViewById(R.id.imageView2);
            url = view.findViewById(R.id.textView12);
            listeners = view.findViewById(R.id.textView18);
        }
    }

    AdapterSearch(Context context, ConstraintLayout root, String[][] tracks) {
        layoutInflater = LayoutInflater.from(context);
        this.root = root;
        this.context = context;
        this.artists = tracks;
    }

    @NonNull
    @Override
    public AdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_search, parent, false);
        return new AdapterSearch.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearch.ViewHolder holder, int position) {
        String[] track_list = artists[position];
        holder.name.setText(track_list[0]);
        holder.url.setText(track_list[1]);
        holder.listeners.setText(track_list[2]);
        Picasso.with(context).load(track_list[3]).into(holder.icon);
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
