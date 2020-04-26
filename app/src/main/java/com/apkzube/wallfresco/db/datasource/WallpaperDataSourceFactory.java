package com.apkzube.wallfresco.db.datasource;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.apkzube.wallfresco.service.PexelsService;

public class WallpaperDataSourceFactory extends DataSource.Factory {
    private PexelsService service;
    private WallpaperDataSource dataSource;
    private Application application;
    private MutableLiveData<WallpaperDataSource> sourceMutableLiveData;

    public WallpaperDataSourceFactory(PexelsService service, Application application) {
        this.service = service;
        this.application = application;
        this.sourceMutableLiveData=new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        dataSource=new WallpaperDataSource(service,application);
        sourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<WallpaperDataSource> getSourceMutableLiveData() {
        return sourceMutableLiveData;
    }
}
