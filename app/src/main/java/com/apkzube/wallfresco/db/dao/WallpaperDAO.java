package com.apkzube.wallfresco.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.apkzube.wallfresco.db.entity.WallpaperEntity;

import java.util.List;

@Dao
public interface WallpaperDAO {

    @Insert
    public String addWallpaper(WallpaperEntity wallpaper);

    @Update
    public void updateWallpaper(WallpaperEntity wallpaper);

    @Delete
    public  void deleteWallpaper(WallpaperEntity wallpaper);

    @Query("SELECT * FROM wallpaper_mst")
    public List<WallpaperEntity> getAllWallpaper();

    @Query("SELECT * FROM wallpaper_mst WHERE id==:wallpaperId")
    public WallpaperEntity getWallpaper(String wallpaperId);

    @Query("SELECT * FROM wallpaper_mst WHERE is_favorite==:"+true)
    public List<WallpaperEntity> getFavoriteWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE is_downloaded==:"+true)
    public List<WallpaperEntity> getDowloadedWallpapers();
}
