package com.apkzube.wallfresco.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.List;

@Dao
public interface WallpaperDAO {

    @Insert
    public String addWallpaper(Wallpaper wallpaper);

    @Update
    public void updateWallpaper(Wallpaper wallpaper);

    @Delete
    public  void deleteWallpaper(Wallpaper wallpaper);

    @Query("SELECT * FROM wallpaper_mst")
    public List<Wallpaper> getAllWallpaper();

    @Query("SELECT * FROM wallpaper_mst WHERE id==:wallpaperId")
    public Wallpaper getWallpaper(String wallpaperId);

    @Query("SELECT * FROM wallpaper_mst WHERE is_favorite==:"+true)
    public List<Wallpaper> getFavoriteWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE is_downloaded==:"+true)
    public List<Wallpaper> getDowloadedWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE category==:category")
    public List<Wallpaper> getCategoryWallpapers(String category);


}
