package com.example.myapplication;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class SearchArtistsRepository {
    private static ArtistsRepository instance = null;
    private List<Artist> dataSet = new ArrayList<>(5);
    String name;

    public SearchArtistsRepository(String name){
        this.name = name;
    }

    public MutableLiveData<List<Artist>> getArtists(){
        setArtists();
        MutableLiveData<List<Artist>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    public void setArtists() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + name + "&api_key=eed53ffdb78ff8f6392bba0925994e93&format=json";

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
                        for (int i = 0; i < a.length(); i++) {
                            String[] pom = new String[4];
                            pom[0] = a.getJSONObject(i).get("name").toString();
                            pom[1] = a.getJSONObject(i).get("url").toString();
                            pom[2] = a.getJSONObject(i).get("listeners").toString();
                            JSONArray b = a.getJSONObject(i).getJSONArray("image");
                            pom[3] = b.getJSONObject(0).get("#text").toString();
                            Artist artist = new Artist(pom[0], pom[2], pom[3], pom[1], 1);
                            dataSet.add(artist);
                        }
                        /*activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                as = new AdapterSearch(activity, dataSet);
                                rv.setAdapter(as);
                            }
                        });*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
