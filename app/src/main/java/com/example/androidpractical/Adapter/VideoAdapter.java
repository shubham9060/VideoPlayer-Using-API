package com.example.androidpractical.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidpractical.Activity.PlayerActivity;
import com.example.androidpractical.Model.Video;
import com.example.androidpractical.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> implements Filterable {
    private final ArrayList<Video> allVideos;
    private static ArrayList<Video> temp;
    private final Context context;

    public VideoAdapter(Context ctx, ArrayList<Video> videos) {
        this.allVideos = videos;
        temp = allVideos;
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,
                parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("SIZEO", String.valueOf(temp.size()));
        holder.title.setText(temp.get(position).getTitle());
        holder.subTitle.setText(temp.get(position).getAuthor());
        holder.thumbnail.setText(temp.get(position).getImageUrl());
        Picasso.get().load("https://thumbs.dreamstime.com/b/rainbow-love-heart-background-red-wood-60045149.jpg")
                .into(holder.videoImage);
        Log.d("Picaso", temp.get(position).getImageUrl());

        holder.vv.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putSerializable("videoData", temp.get(holder.getAdapterPosition()));
            Intent i = new Intent(context, PlayerActivity.class);
            i.putExtras(b);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            Log.e("SIZE", String.valueOf(temp.size()));
            Log.e("SIZE", String.valueOf(allVideos.size()));
            ArrayList<Video> filterdata = new ArrayList<>();
            if (keyword.toString().isEmpty()) {
            } else {
                for (Video obj : allVideos) {
                    if (obj.getTitle().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filterdata.add(obj);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdata;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            temp.clear();
            temp.addAll((ArrayList<Video>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImage;
        TextView title, subTitle, thumbnail;
        View vv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.videoThumbnail);
            title = itemView.findViewById(R.id.videoTitle);
            subTitle = itemView.findViewById(R.id.tvSubTitle);
            thumbnail = itemView.findViewById(R.id.tvThumb);
            vv = itemView;
        }
    }
}
