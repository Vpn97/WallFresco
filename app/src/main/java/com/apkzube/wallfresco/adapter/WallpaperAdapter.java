package com.apkzube.wallfresco.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.ui.wallpaper.WallpaperViewModel;

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
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
