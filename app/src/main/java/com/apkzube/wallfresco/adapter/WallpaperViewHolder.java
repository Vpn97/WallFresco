package com.apkzube.wallfresco.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.apkzube.wallfresco.databinding.ItemWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

class WallpaperViewHolder extends RecyclerView.ViewHolder {
    private ItemWallpaperBinding mBinding;

    public WallpaperViewHolder(@NonNull ItemWallpaperBinding mBinding) {
        super(mBinding.getRoot());
        this.mBinding=mBinding;
    }

    public void setData(Wallpaper wallpaper, Context context) {

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
}
