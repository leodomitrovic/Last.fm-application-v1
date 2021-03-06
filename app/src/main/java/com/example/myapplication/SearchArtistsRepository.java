package com.example.myapplication;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.revinate.guava.util.concurrent.RateLimiter;

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
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchArtistsRepository implements Interceptor {
    private RateLimiter limiter = RateLimiter.create(3);
    private List<Artist> dataSet;
    AppDatabase db;

    public SearchArtistsRepository(Application app){
        db = Room.databaseBuilder(app, AppDatabase.class, "images").build();
    }

    public MutableLiveData<List<Artist>> setArtists(String name) {
        dataSet = new ArrayList<>();
        MutableLiveData<List<Artist>> data = new MutableLiveData<>();
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
        String url = "https://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + name + "&api_key=&format=json";

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
                    String artists = Objects.requireNonNull(response.body()).string();
                    try {
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(artists);
                        JSONObject js = jsonObject.getJSONObject("results");
                        JSONObject o = js.getJSONObject("artistmatches");
                        JSONArray a = o.getJSONArray("artist");
                        for (int i = 0; i < 30; i++) { //30
                            String[] pom = new String[4];
                            pom[0] = a.getJSONObject(i).get("name").toString();
                            pom[1] = a.getJSONObject(i).get("url").toString();
                            pom[2] = a.getJSONObject(i).get("listeners").toString();
                            JSONArray b = a.getJSONObject(i).getJSONArray("image");
                            pom[3] = b.getJSONObject(0).get("#text").toString();
                            Artist artist = new Artist(pom[0], pom[2], Uri.parse(pom[3]), pom[1], 1);
                            String artist1 = artist.name;
                            final String artist_pom = artist1.replace(" ", "%20");
                            ArtistEntity ae = db.artistDao().findByName(artist_pom);
                            if (ae != null) {
                                artist.icon = Uri.parse(ae.image);
                                dataSet.add(artist);
                                data.postValue(dataSet);
                                continue;
                            }
                            dataSet.add(artist);
                            data.postValue(dataSet);
                            OkHttpClient client1 = new OkHttpClient.Builder()
                                    .addInterceptor(SearchArtistsRepository.this)
                                    .build();

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
                                    dataSet.add(artist);
                                    data.postValue(dataSet);
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        String artist2 = Objects.requireNonNull(response.body()).string();
                                        try {
                                            JSONObject jsonObject = new JSONObject(artist2);
                                            JSONArray o = jsonObject.getJSONArray("value");
                                            String thumbnail = o.getJSONObject(0).getString("thumbnailUrl");
                                            artist.icon = Uri.parse(thumbnail);
                                            ArtistEntity novi = new ArtistEntity();
                                            novi.artist_name = artist_pom;
                                            novi.image = thumbnail;
                                            db.artistDao().insertAll(novi);
                                            dataSet.add(artist);
                                            data.postValue(dataSet);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    response.body().close();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                response.body().close();
            }
        });
        return data;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        limiter.acquire(3);
        return chain.proceed(chain.request());
    }
}
