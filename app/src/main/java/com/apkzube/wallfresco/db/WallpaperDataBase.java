package com.apkzube.wallfresco.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.apkzube.wallfresco.db.dao.WallpaperDAO;
import com.apkzube.wallfresco.db.entity.WallpaperEntity;

@Database(entities = {WallpaperEntity.class}, version = 1)
public abstract class WallpaperDataBase extends RoomDatabase {

    public abstract WallpaperDAO getWallaperDAO();

}
