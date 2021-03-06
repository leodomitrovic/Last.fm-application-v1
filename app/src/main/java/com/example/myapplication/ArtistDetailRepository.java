package com.example.myapplication;


import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

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

public class ArtistDetailRepository {
    private Artist artist;
    private List<Track> dataSet = new ArrayList<>();
    AppDatabase db;

    public ArtistDetailRepository(Application app) {
        db = Room.databaseBuilder(app, AppDatabase.class, "images").build();
    }

    public MutableLiveData<Artist> setData(String name) {
        artist = null;
        MutableLiveData<Artist> data = new MutableLiveData<>();
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
        String artist1 = name;
        artist1 = artist1.replace(" ", "+");
        String url = "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artist1 + "&api_key=&format=json";
        final Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("pogreska", e.getMessage());
                data.postValue(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String info = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(info);
                        JSONObject js = jsonObject.getJSONObject("artist");
                        String[] pom = new String[6];
                        pom[0] = js.get("name").toString();
                        pom[1] = js.get("url").toString();
                        JSONArray a = js.getJSONArray("image");
                        pom[2] = a.getJSONObject(0).get("#text").toString();
                        JSONObject b = js.getJSONObject("stats");
                        if (b != null) {
                            pom[3] = b.get("listeners").toString();
                            pom[4] = b.get("playcount").toString();
                            if (!js.get("tags").equals("")) {
                                b = js.getJSONObject("tags");
                                JSONArray tag = b.getJSONArray("tag");
                                if (tag != null) {
                                    pom[5] = tag.getJSONObject(0).getString("name");
                                }
                            }
                        }
                        artist = new Artist(pom[0], pom[3], Uri.parse(pom[2]), pom[4], pom[1], pom[5]);
                        data.postValue(artist);
                        String artist1 = name;
                        final String artist_pom = artist1;/*.replace(" ", "%20");*/
                        ArtistEntity ae = db.artistDao().findByName(artist_pom);
                        if (ae != null) {
                            artist.icon = Uri.parse(ae.image);
                            data.postValue(artist);
                            return;
                        }
                        OkHttpClient client1 = new OkHttpClient();
                        String url1 = "https://bing-image-search1.p.rapidapi.com/images/search?q=" + artist_pom;
                        Request request1 = new Request.Builder()
                                .url(url1)
                                .get()
                                .addHeader("x-rapidapi-key", "")
                                .addHeader("x-rapidapi-host", "bing-image-search1.p.rapidapi.com")
                                .build();
                        client1.newCall(request1).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                System.out.println("Failure");
                                data.postValue(artist);
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    String artist2 = Objects.requireNonNull(response.body()).string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(artist2);
                                        JSONArray o = jsonObject.getJSONArray("value");
                                        String thumbnail = o.getJSONObject(0).getString("thumbnailUrl");
                                        pom[2] = thumbnail;
                                        ArtistEntity novi = new ArtistEntity();
                                        novi.artist_name = artist_pom;
                                        novi.image = thumbnail;
                                        db.artistDao().insertAll(novi);
                                        artist.icon = Uri.parse(pom[2]);
                                        data.postValue(artist);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    data.postValue(artist);
                }
            }
        });
        return data;
    }

    public MutableLiveData<List<Track>> setTracks(String name) {
        MutableLiveData<List<Track>> data = new MutableLiveData<>();
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
        String artist1 = name;
        if (artist1.contains(" ")) {
            artist1.replace(" ", "+");
        }
        String url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=" + artist1 + "&api_key=&format=json";
        final Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("pogreska", e.getMessage());
                data.postValue(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String tracks = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject;
                        JSONArray o;
                        jsonObject = new JSONObject(tracks);
                        JSONObject js = jsonObject.getJSONObject("toptracks");
                        o = js.getJSONArray("track");
                        for (int i = 0; i < o.length(); i++) {
                            String[] pom = new String[5];
                            pom[0] = o.getJSONObject(i).get("name").toString();
                            pom[1] = o.getJSONObject(i).get("listeners").toString();
                            pom[2] = o.getJSONObject(i).get("playcount").toString();
                            pom[3] = name;
                            JSONArray a = o.getJSONObject(o.getJSONObject(i).length()).getJSONArray("image");
                            pom[4] = a.getJSONObject(0).get("#text").toString();
                            Track track = new Track(pom[0], pom[1], Uri.parse(pom[4]), pom[2], name);
                            dataSet.add(track);
                        }
                        data.postValue(dataSet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return data;
    }
}
