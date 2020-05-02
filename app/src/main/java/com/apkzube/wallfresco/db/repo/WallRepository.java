package com.apkzube.wallfresco.db.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.apkzube.wallfresco.db.WallpaperDataBase;
import com.apkzube.wallfresco.db.dao.WallpaperDAO;
import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WallRepository {

    private Application application;
    private WallpaperDAO wallpaperDAO;
    private Executor executor;

    public WallRepository(Application application) {
        this.application = application;
        this.executor = Executors.newFixedThreadPool(5);
        this.wallpaperDAO = WallpaperDataBase.getInstance(application.getApplicationContext()).getWallpaperDAO();
    }


    public void insertWallpaper(Wallpaper wallpaper) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                wallpaperDAO.insertWallpaper(wallpaper);
            }
        });
    }


    public void insertAllWallpapers(List<Wallpaper> wallpapers) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                wallpaperDAO.insertAllWallpaper(wallpapers);
            }
        });
    }

    public void updateWallpaper(Wallpaper wallpaper) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                wallpaperDAO.updateWallpaper(wallpaper);
            }
        });
    }

    public void deleteWallpaper(Wallpaper wallpaper) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                wallpaperDAO.deleteWallpaper(wallpaper);
            }
        });
    }

    public LiveData<List<Wallpaper>> getAllWallpaper() {
        return wallpaperDAO.getAllWallpaper();
    }

    public LiveData<List<Wallpaper>> getPortraitWallpapers() {
        return wallpaperDAO.getPortraitWallpapers();
    }

    public LiveData<List<Wallpaper>> getLimitedWallpapers(int mLimit) {
        return wallpaperDAO.getWallpapers(mLimit);
    }

    public DataSource.Factory<Integer, Wallpaper> getCategoryWallpapers(String category) {
        return wallpaperDAO.getCategoryWallpapers(category);
    }

    public LiveData<List<Wallpaper>> getFavoriteWallpapers() {
        return wallpaperDAO.getFavoriteWallpapers();
    }

    public LiveData<List<Wallpaper>> getDownloadedWallpapers() {
        return wallpaperDAO.getDownloadedWallpapers();
    }

    public LiveData<Wallpaper> getWallpaper(String wallpaperId) {
        return wallpaperDAO.getWallpaper(wallpaperId);
    }

    public LiveData<List<Wallpaper>> getTrendWallpapers() {
        return wallpaperDAO.getTrendWallpapers();
    }

    public DataSource.Factory<Integer, Wallpaper> getAllPagedWallpaper() {
            return wallpaperDAO.getAllPagedWallpaper();
    }
}
