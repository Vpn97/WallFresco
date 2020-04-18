package com.apkzube.wallfresco.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.db.dao.WallpaperDAO;
import com.apkzube.wallfresco.db.entity.Wallpaper;

@Database(entities = {Wallpaper.class}, version = 1,exportSchema = false)
public abstract class WallpaperDataBase extends RoomDatabase {

    private static WallpaperDataBase instance;

    public abstract WallpaperDAO getWallaperDAO();

    public static synchronized WallpaperDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WallpaperDataBase.class, context.getString(R.string.wallpaper_db_key))
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
