package com.example.myapplication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistsRepository {
    private static ArtistsRepository instance = null;
    private List<Artist> dataSet = new ArrayList<>(5);

    public static ArtistsRepository getInstance(){
        if(instance == null){
            instance = new ArtistsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Artist>> getArtists() {
        setArtists();
        MutableLiveData<List<Artist>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setArtists() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
        String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&api_key=&format=json";

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
                        JSONArray o;
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(artists);
                        JSONObject js = jsonObject.getJSONObject("artists");
                        o = js.getJSONArray("artist");
                        for (int i = 0; i < o.length(); i++) {
                            String[] pom = new String[4];
                            pom[0] = o.getJSONObject(i).get("name").toString();
                            pom[1] = o.getJSONObject(i).get("listeners").toString();
                            pom[2] = o.getJSONObject(i).get("playcount").toString();
                            JSONArray p = o.getJSONObject(o.getJSONObject(i).length()).getJSONArray("image");
                            pom[3] = p.getJSONObject(0).get("#text").toString();
                            Artist a = new Artist(pom[0], pom[1], pom[3], pom[2]);
                            dataSet.add(a);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
