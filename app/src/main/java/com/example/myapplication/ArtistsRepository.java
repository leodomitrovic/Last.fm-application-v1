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

public class ArtistsRepository implements Interceptor {
    private RateLimiter limiter = RateLimiter.create(5);
    private List<Artist> dataSet = new ArrayList<>();
    AppDatabase db;

    public ArtistsRepository(Application app){
        db = Room.databaseBuilder(app, AppDatabase.class, "images").build();
    }

    public MutableLiveData<List<Artist>> setArtists() {
        MutableLiveData<List<Artist>> data = new MutableLiveData<>();

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
                data.postValue(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
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
                            Artist a = new Artist(pom[0], pom[1], Uri.parse(pom[3]), pom[2]);
                            dataSet.add(a);
                        }
                        data.postValue(dataSet);
                        for (int i = 0; i < dataSet.size(); i++) {
                            String artist1 = dataSet.get(i).name;
                            final int index = i;
                            final String artist_pom = artist1.replace(" ", "+");
                            ArtistEntity ae = db.artistDao().findByName(artist_pom);
                            if (ae != null) {
                                dataSet.get(i).icon = Uri.parse(ae.image);
                                data.postValue(dataSet);
                                continue;
                            }

                            OkHttpClient client1 = new OkHttpClient.Builder()
                                    .addInterceptor(ArtistsRepository.this)
                                    .build();

                            String url1 = "https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/Search/ImageSearchAPI?q=" + artist_pom + "+head&pageNumber=1&pageSize=1&safeSearch=true";
                            Request request1 = new Request.Builder()
                                    .url(url1)
                                    .get()
                                    .addHeader("x-rapidapi-key", "")
                                    .addHeader("x-rapidapi-host", "contextualwebsearch-websearch-v1.p.rapidapi.com")
                                    .build();
                            client1.newCall(request1).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                    System.out.println("Failure");
                                    data.postValue(dataSet);
                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                    if (response.isSuccessful()) {
                                        System.out.println("Response");
                                        String artist = Objects.requireNonNull(response.body()).string();
                                        try {
                                            JSONObject jsonObject = new JSONObject(artist);
                                            JSONArray o = jsonObject.getJSONArray("value");
                                            String thumbnail = o.getJSONObject(0).getString("url");
                                            dataSet.get(index).icon = Uri.parse(thumbnail);
                                            ArtistEntity novi = new ArtistEntity();
                                            novi.artist_name = artist_pom;
                                            novi.image = thumbnail;
                                            db.artistDao().insertAll(novi);
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
        limiter.acquire(5);
        return chain.proceed(chain.request());
    }
}
