package com.example.androidpractical.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidpractical.Adapter.VideoAdapter;
import com.example.androidpractical.Model.Video;
import com.example.androidpractical.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView videoList;
    private VideoAdapter adapter;
    public static final String TAG = "TAG";
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    SharedPreferences sharedpreferences;
    private Button logoutBtn;
    private ArrayList<Video> all_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPref();
        all_videos = new ArrayList<>();
        logoutBtn = findViewById(R.id.idBtnLogout);
        videoList = findViewById(R.id.videoList);
        getJsonData();
        logOut();
        recyclerVideoList();
    }

    private void recyclerVideoList() {
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, all_videos);
        videoList.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void sharedPref() {
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString(EMAIL_KEY, null);
        TextView welcomeTV = findViewById(R.id.idTVWelcome);
        welcomeTV.setText("Welcome" + " " + email);
    }

    private void logOut() {
        logoutBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void getJsonData() {
        String URL = "https://run.mocky.io/v3/9dab915c-85b9-4ea6-8d6f-33fe992d8ae3";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            Log.d(TAG, "onResponse: " + response);
            try {
                JSONArray videos = response.getJSONArray("videos");
                Log.d(TAG, "onResponse: " + response);
                for (int i = 0; i < videos.length(); i++) {
                    JSONObject video = videos.getJSONObject(i);
                    Log.d(TAG, "onResponse: " + video.getString("title"));
                    Log.d(TAG, "onresponse:" + video.getString("thumb"));
                    Video v = new Video();
                    v.setTitle(video.getString("title"));
                    v.setDescription(video.getString("description"));
                    v.setAuthor(video.getString("subtitle"));
                    v.setImageUrl(video.getString("thumb"));
                    v.setVideoUrl(video.getString("sources"));
                    all_videos.add(v);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: " + v.getVideoUrl());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d(TAG, "onErrorResponse: " + error.getMessage()));
        requestQueue.add(objectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}