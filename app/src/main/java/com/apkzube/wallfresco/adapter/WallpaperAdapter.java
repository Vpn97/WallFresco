package com.apkzube.wallfresco.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.activity.SetWallpaper;
import com.apkzube.wallfresco.databinding.ItemWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
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
        holder.setData(wallpaper);
        holder.getmBinding().setWallpaper(wallpaper);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);

        holder.itemView.startAnimation(animation);

                /*AlphaAnimation aa1 = new AlphaAnimation(1.0f, 0.1f);
                aa1.setDuration(400);
                holder.imgWallpaper.startAnimation(aa1);*/

        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(400);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, SetWallpaper.class);
            intent.putExtra(context.getString(R.string.wallpaper_obj_key),wallpapers.get(position));
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                    ((Activity) context, holder.getmBinding().imgWallpaper, "img").toBundle());

        });


        // TODO holder click listener
    }

    @Override
    public int getItemCount() {
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
                    .thumbnail(Glide // this thumbnail request has to have the same RESULT cache key
                            .with(context) // as the outer request, which usually simply means
                            .load(Uri.parse(wallpaper.getPortrait())) // same size/transformation(e.g. centerCrop)/format(e.g. asBitmap)
                            .fitCenter())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //mBinding.wallpaperLoading.setVisibility(View.INVISIBLE);
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
