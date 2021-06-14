package com.example.myapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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

public class TracksRepository {
    private static TracksRepository instance;
    private List<Track> dataSet = new ArrayList<>(5);

    public static TracksRepository getInstance(){
        if(instance == null){
            instance = new TracksRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Track>> getTracks(){
        setTracks();
        MutableLiveData<List<Track>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    void setTracks() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .writeTimeout(7, TimeUnit.SECONDS)
                .readTimeout(7, TimeUnit.SECONDS)
                .build();
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
                        JSONArray o;
                        jsonObject = new JSONObject(tracks);
                        JSONObject js = jsonObject.getJSONObject("tracks");
                        o = js.getJSONArray("track");
                        for (int i = 0; i < o.length(); i++) {
                            String[] pom = new String[5];
                            pom[0] = o.getJSONObject(i).get("name").toString();
                            pom[1] = o.getJSONObject(i).get("listeners").toString();
                            pom[2] = o.getJSONObject(i).get("playcount").toString();
                            JSONObject b = o.getJSONObject(i).getJSONObject("artist");
                            pom[3] = b.get("name").toString();
                            JSONArray a = o.getJSONObject(o.getJSONObject(i).length()).getJSONArray("image");
                            pom[4] = a.getJSONObject(0).get("#text").toString();
                            Track t = new Track(pom[0], pom[1], pom[4], pom[2], pom[3]);
                            dataSet.add(t);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
