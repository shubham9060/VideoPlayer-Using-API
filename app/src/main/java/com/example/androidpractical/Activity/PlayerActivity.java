package com.example.androidpractical.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpractical.Model.Video;
import com.example.androidpractical.R;

import java.util.Objects;

public class PlayerActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private VideoView videoPlayer;
    private ProgressBar spiiner;
    private TextView title;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        spiiner = findViewById(R.id.progressBar);
        title = findViewById(R.id.videoTitle);
        desc = findViewById(R.id.videoDesc);
        videoPlayer = findViewById(R.id.videoView);
        getData();

    }

    private void getData() {
        Intent i = getIntent();
        Bundle data = i.getExtras();
        Video v = (Video) data.getSerializable("videoData");
        Objects.requireNonNull(getSupportActionBar()).setTitle(v.getTitle());
        Log.d(TAG, "onCreate" + v.getTitle());
        title.setText(v.getTitle());
        desc.setText(v.getDescription());
        Uri videoUrl = Uri.parse(v.getVideoUrl());
        videoPlayer.setVideoURI(videoUrl);
        MediaController mc = new MediaController(this);
        videoPlayer.setMediaController(mc);
        videoPlayer.setOnPreparedListener(mp -> {
            videoPlayer.start();
            spiiner.setVisibility(View.GONE);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}