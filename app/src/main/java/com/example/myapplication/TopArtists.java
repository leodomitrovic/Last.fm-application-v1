package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TopArtists extends AppCompatActivity {
    RecyclerView rv;
    AdapterArtists aa;
    JSONArray o;
    List<String[]> artist_list;
    static ConstraintLayout root;
    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_artists);
        rv = findViewById(R.id.rv);
        root = findViewById(R.id.root);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getArtists();
        aa = new AdapterArtists(this, root, artist_list);
        rv.setAdapter(aa);
        //show();
    }

    public void getArtists() {
        String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=" + " " + "&format=json";
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
                        JSONObject js = jsonObject.getJSONObject("artists");
                        o = js.getJSONArray("artist");
                        for (int i = 0; i < o.length(); i++) {
                            try {
                                String[] pom = new String[o.length()];
                                pom[0] = o.getJSONObject(i).getJSONObject("name").toString();
                                pom[1] = o.getJSONObject(i).getJSONObject("listeners").toString();
                                pom[2] = o.getJSONObject(i).getJSONObject("playcount").toString();
                                pom[3] = o.getJSONObject(i).getJSONObject("icon").toString();
                                artist_list.add(pom);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Artists not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public void setup(JSONArray a) {
        o = a;
    }

    public void show() {
        for (int i = 0; i < o.length(); i++) {
            try {
                String[] pom = new String[o.length()];
                pom[0] = o.getJSONObject(i).getJSONObject("name").toString();
                pom[1] = o.getJSONObject(i).getJSONObject("listeners").toString();
                pom[2] = o.getJSONObject(i).getJSONObject("playcount").toString();
                pom[3] = o.getJSONObject(i).getJSONObject("icon").toString();
                artist_list.add(pom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}