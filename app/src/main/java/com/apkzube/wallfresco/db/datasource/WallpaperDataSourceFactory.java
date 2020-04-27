package com.apkzube.wallfresco.db.datasource;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;

public class WallpaperDataSourceFactory extends DataSource.Factory {
    private WallRepository repository;
    private DataSource<Integer,Wallpaper> dataSource;
    private Application application;
    private MutableLiveData<DataSource<Integer, Wallpaper>> sourceMutableLiveData;

    public WallpaperDataSourceFactory(WallRepository repository, Application application) {
        this.application = application;
        this.repository=new WallRepository(application);
        this.sourceMutableLiveData=new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer,Wallpaper> create() {
       // dataSource=repository.getAllPagedWallpaper();
        sourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<DataSource<Integer,Wallpaper>> getSourceMutableLiveData() {
        return sourceMutableLiveData;
    }
}
