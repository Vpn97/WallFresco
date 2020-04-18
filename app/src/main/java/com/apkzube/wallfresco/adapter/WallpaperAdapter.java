package com.apkzube.wallfresco.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.ItemWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.ui.wallpaper.WallpaperViewModel;
import com.apkzube.wallfresco.util.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {
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

        holder.setData(wallpaper);
        holder.getmBinding().setWallpaper(wallpaper);

        // TODO holder click listener
    }

    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "getItemCount: "+wallpapers.size());
        return wallpapers.size();
    }


    class WallpaperViewHolder extends RecyclerView.ViewHolder{
        private ItemWallpaperBinding mBinding;

        public WallpaperViewHolder(@NonNull ItemWallpaperBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding=mBinding;
        }

        public void setData(Wallpaper wallpaper) {

            Glide.with(context)
                    .load(Uri.parse(wallpaper.getPortrait()))
                    .thumbnail(0.1f)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mBinding.wallpaperLoading.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    })
                    .into(mBinding.imgWallpaper);
        }

        public ItemWallpaperBinding getmBinding() {
            return mBinding;
        }

        public void setmBinding(ItemWallpaperBinding mBinding) {
            this.mBinding = mBinding;
        }
    }

}
