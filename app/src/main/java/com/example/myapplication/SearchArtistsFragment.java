package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchArtistsFragment extends Fragment {
    RecyclerView rv;
    AdapterSearch as;
    String[][] artist_list;
    Activity activity;
    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .build();

    public SearchArtistsFragment() {
        // Required empty public constructor
    }

    public SearchArtistsFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_artists, container, false);
        rv = view.findViewById(R.id.rv2);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        ImageView search = view.findViewById(R.id.imageView4);
        EditText e = view.findViewById(R.id.editTextTextPersonName2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArtists(activity, e.getText().toString());
            }
        });
        return  view;
    }

    public void getArtists(Activity activity, String s) {
        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + s + "&api_key=&format=json";

        final Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("pogreska", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String artists = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(artists);
                        JSONObject js = jsonObject.getJSONObject("results");
                        JSONObject o = js.getJSONObject("artistmatches");
                        JSONArray a = o.getJSONArray("artist");
                        artist_list = new String[a.length()][a.getJSONObject(0).length()];
                        for (int i = 0; i < a.length(); i++) {
                            String[] pom = new String[4];
                            pom[0] = a.getJSONObject(i).get("name").toString();
                            pom[1] = a.getJSONObject(i).get("url").toString();
                            pom[2] = a.getJSONObject(i).get("listeners").toString();
                            JSONArray b = a.getJSONObject(i).getJSONArray("image");
                            pom[3] = b.getJSONObject(0).get("#text").toString();
                            artist_list[i] = pom;
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                as = new AdapterSearch(activity, artist_list);
                                rv.setAdapter(as);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Artists not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}