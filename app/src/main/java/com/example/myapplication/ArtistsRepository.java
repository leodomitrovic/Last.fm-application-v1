package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.SystemClock;
import android.text.style.ScaleXSpan;
import android.util.Log;
import android.widget.SearchView;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistsRepository {
    private static ArtistsRepository instance = null;
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
                            if (artist1.contains(" ")) {
                                artist1.replace(" ", "%20");
                            }
                            ArtistEntity ae = db.artistDao().findByName(artist1);
                            if (ae != null) {
                                dataSet.get(i).icon = Uri.parse(ae.image);
                                data.postValue(dataSet);
                                continue;
                            }

                            Dispatcher dispatcher = new Dispatcher();
                            dispatcher.setMaxRequests(1);

                            Interceptor interceptor = new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    SystemClock.sleep(2000);
                                    return chain.proceed(chain.request());
                                }
                            };

                            OkHttpClient client1 = new OkHttpClient.Builder()
                                    .addNetworkInterceptor(interceptor)
                                    .dispatcher(dispatcher)
                                    .build();

                            String url1 = "https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/Search/ImageSearchAPI?q=" + artist1 + "%20artist%20face&pageNumber=1&pageSize=2&autoCorrect=true&safeSearch=true";
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
                                        String artist = Objects.requireNonNull(response.body()).string();
                                        System.out.println("Ajmo " + artist);
                                        try {
                                            JSONObject jsonObject = new JSONObject(artist);
                                            JSONArray o = jsonObject.getJSONArray("value");
                                            String thumbnail = o.getJSONObject(1).getString("url");
                                            dataSet.get(index).icon = Uri.parse(thumbnail);
                                            ArtistEntity novi = new ArtistEntity();
                                            novi.artist_name = artist1;
                                            novi.image = thumbnail;
                                            db.artistDao().insertAll(novi);
                                            data.postValue(dataSet);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return data;
    }
}
