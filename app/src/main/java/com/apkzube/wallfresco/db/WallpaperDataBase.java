package com.apkzube.wallfresco.db;


import androidx.room.Database;

import com.apkzube.wallfresco.db.dao.WallpaperDAO;
import com.apkzube.wallfresco.db.entity.WallpaperEntity;

@Database(entities = {WallpaperEntity.class}, version = 1)
public abstract class WallpaperDataBase {

    public abstract WallpaperDAO getWallaperDAO();

}
