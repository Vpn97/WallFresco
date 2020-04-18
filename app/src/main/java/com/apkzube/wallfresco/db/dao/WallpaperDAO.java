package com.apkzube.wallfresco.db.dao;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    public void addWallpaper(Wallpaper wallpaper);

    @Update
    public void updateWallpaper(Wallpaper wallpaper);

    @Delete
    public  void deleteWallpaper(Wallpaper wallpaper);

    @Query("SELECT * FROM wallpaper_mst")
    public List<Wallpaper> getAllWallpaper();

    @Query("SELECT * FROM wallpaper_mst WHERE id==:wallpaperId")
    public LiveData<Wallpaper> getWallpaper(String wallpaperId);

    @Query("SELECT * FROM wallpaper_mst WHERE is_favorite= 1")
    public LiveData<Wallpaper> getFavoriteWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE is_downloaded= 1")
    public LiveData<Wallpaper> getDowloadedWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE category==:category")
    public LiveData<Wallpaper> getCategoryWallpapers(String category);


}
