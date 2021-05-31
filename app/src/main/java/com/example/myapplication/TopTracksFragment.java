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

public class TopTracksFragment extends Fragment {
    RecyclerView rv;
    AdapterTracks at;
    String[][] tracks_list;
    JSONArray o;
    Activity activity;
    public final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(7, TimeUnit.SECONDS)
            .writeTimeout(7, TimeUnit.SECONDS)
            .readTimeout(7, TimeUnit.SECONDS)
            .build();

    public TopTracksFragment() {
        // Required empty public constructor
    }

    public TopTracksFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_top_tracks, container, false);
        getTracks(activity);
        rv = view.findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    void getTracks(Activity activity) {
        String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=eed53ffdb78ff8f6392bba0925994e93&format=json";
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
                    String tracks = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(tracks);
                        JSONObject js = jsonObject.getJSONObject("tracks");
                        o = js.getJSONArray("track");
                        tracks_list = new String[o.length()][o.getJSONObject(0).length()];
                        for (int i = 0; i < o.length(); i++) {
                            String[] pom = new String[5];
                            pom[0] = o.getJSONObject(i).get("name").toString();
                            pom[1] = o.getJSONObject(i).get("listeners").toString();
                            pom[2] = o.getJSONObject(i).get("playcount").toString();
                            JSONObject b = o.getJSONObject(i).getJSONObject("artist");
                            pom[3] = b.get("name").toString();
                            JSONArray a = o.getJSONObject(o.getJSONObject(i).length()).getJSONArray("image");
                            pom[4] = a.getJSONObject(0).get("#text").toString();
                            tracks_list[i] = pom;
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                at = new AdapterTracks(activity.getApplicationContext(), tracks_list);
                                rv.setAdapter(at);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Tracks not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}