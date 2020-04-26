package com.apkzube.wallfresco.util;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.ArrayList;

public class WallpaperDiffCallback extends DiffUtil.Callback {

    ArrayList<Wallpaper> oldList;
    ArrayList<Wallpaper> newList;

    public WallpaperDiffCallback(ArrayList<Wallpaper> oldList, ArrayList<Wallpaper> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId()==newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(oldItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
