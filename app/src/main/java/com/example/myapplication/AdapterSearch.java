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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {
    private final LayoutInflater layoutInflater;
    String[][] artists;
    Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, url, listeners;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.textView11);
            icon = view.findViewById(R.id.imageView2);
            url = view.findViewById(R.id.textView12);
            listeners = view.findViewById(R.id.textView18);
        }
    }

    AdapterSearch(Activity activity, String[][] tracks) {
        layoutInflater = LayoutInflater.from(activity.getApplicationContext());
        this.activity = activity;
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
        Picasso.with(activity.getApplicationContext()).load(track_list[3]).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fManager = activity.getFragmentManager();
                Fragment f;
                f = new ArtistDetailFragment1(activity, holder.name.getText().toString());
                fManager.beginTransaction().replace(R.id.container, f).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artists.length;
    }
}
