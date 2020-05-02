package com.apkzube.wallfresco.ui.wallpaper;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.apkzube.wallfresco.db.callbacks.WallpaperBoundaryCallback;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.service.PexelsService;
import com.apkzube.wallfresco.service.impl.PexelsServiceImpl;
import com.apkzube.wallfresco.util.CommonRestURL;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WallpaperViewModel extends AndroidViewModel {

    private Application application;
    private WallRepository repository;
    private Executor executor;
    private LiveData<PagedList<Wallpaper>> wallpaperPagedList;
    private MutableLiveData<String> searchLiveData=new MutableLiveData<>();
    private DataSource.Factory<Integer, Wallpaper> factory;
    private DataSource.Factory<Integer, Wallpaper> searchFactory;
    private PexelsService service;
    private PagedList.Config config;

    public WallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.repository = new WallRepository(application);
        this.service = PexelsServiceImpl.getService();
        this.factory = repository.getAllPagedWallpaper();

        this.executor = Executors.newFixedThreadPool(5);
        this.config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPageSize(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPrefetchDistance(4)
                .build();



        wallpaperPagedList= Transformations.switchMap(searchLiveData,input -> {
            if(input==null || TextUtils.isEmpty(input)){
                WallpaperBoundaryCallback boundaryCallback = new WallpaperBoundaryCallback(repository, service,input);
                return (new LivePagedListBuilder<Integer, Wallpaper>(factory, config))
                        .setFetchExecutor(executor)
                        .setBoundaryCallback(boundaryCallback)
                        .build();
            }else{
                WallpaperBoundaryCallback boundaryCallback = new WallpaperBoundaryCallback(repository, service,input);
                this.searchFactory = repository.getCategoryWallpapers(input);
                return (new LivePagedListBuilder<Integer, Wallpaper>(searchFactory, config))
                        .setFetchExecutor(executor)
                        .setBoundaryCallback(boundaryCallback)
                        .build();
            }
        });

    }


    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LiveData<PagedList<Wallpaper>> getWallpaperPagedList() {
        return wallpaperPagedList;
    }

    public MutableLiveData<String> getSearchLiveData() {
        return searchLiveData;
    }

    public void setSearchLiveData(MutableLiveData<String> searchLiveData) {
        this.searchLiveData = searchLiveData;
    }
}