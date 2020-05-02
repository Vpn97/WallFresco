package com.apkzube.wallfresco.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.List;

@Dao
public interface WallpaperDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertWallpaper(Wallpaper wallpaper);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAllWallpaper(List<Wallpaper> wallpapers);

    @Update
    public void updateWallpaper(Wallpaper wallpaper);

    @Delete
    public  void deleteWallpaper(Wallpaper wallpaper);

    @Query("SELECT * FROM wallpaper_mst WHERE is_trending=0 AND category is null")
    public LiveData<List<Wallpaper>> getAllWallpaper();


    @Query("SELECT * FROM wallpaper_mst WHERE is_trending=0 AND CAST(width AS INT) < CAST(height AS INT) order by created_date")
    public DataSource.Factory<Integer,Wallpaper> getAllPagedWallpaper();


    @Query("SELECT * FROM wallpaper_mst WHERE is_trending=1")
    public LiveData<List<Wallpaper>> getTrendWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE CAST(width AS INT) < CAST(height AS INT) AND is_trending=0 AND category is null")
    public LiveData<List<Wallpaper>> getPortraitWallpapers();


    @Query("SELECT * FROM wallpaper_mst WHERE id==:wallpaperId")
    public LiveData<Wallpaper> getWallpaper(String wallpaperId);

    @Query("SELECT * FROM wallpaper_mst WHERE is_favorite=1")
    public LiveData<List<Wallpaper>> getFavoriteWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE is_downloaded= 1")
    public LiveData<List<Wallpaper>> getDownloadedWallpapers();

    @Query("SELECT * FROM wallpaper_mst WHERE category ==:category")
    public DataSource.Factory<Integer,Wallpaper> getCategoryWallpapers(String category);

    @Query("SELECT * FROM wallpaper_mst limit :mLimit")
    public LiveData<List<Wallpaper>> getWallpapers(int mLimit);



}
