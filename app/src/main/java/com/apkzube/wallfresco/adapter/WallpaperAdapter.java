package com.apkzube.wallfresco.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.ItemWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.ui.wallpaper.WallpaperViewModel;
import com.apkzube.wallfresco.util.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperViewHolder> {
    Context context;
    ArrayList<Wallpaper> wallpapers;

    public WallpaperAdapter(Context context, ArrayList<Wallpaper> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemWallpaperBinding binding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_wallpaper,viewGroup,false);
        return  new WallpaperViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        Wallpaper wallpaper=wallpapers.get(position);
        Log.d(Constant.TAG, "onBindViewHolder: "+new Gson().toJson(wallpaper));

        holder.setData(wallpaper,context);
        holder.getmBinding().setWallpaper(wallpaper);

        // TODO holder click listener
    }

    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "getItemCount: "+wallpapers.size());
        return wallpapers.size();
    }
}
