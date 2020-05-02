package com.apkzube.wallfresco.ui.wallpaper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

public class WallpaperViewModel extends AndroidViewModel{

    private Application application;
    private WallRepository repository;
    private Executor executor;
    private LiveData<PagedList<Wallpaper>> wallpaperPagedList;
    private String search;

    public WallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        this.search=null;
        repository=new WallRepository(application);
        PexelsService service= PexelsServiceImpl.getService();
        DataSource.Factory<Integer,Wallpaper> factory=repository.getAllPagedWallpaper(search);

        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPageSize(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPrefetchDistance(4)
                .build();

        WallpaperBoundaryCallback boundaryCallback=new WallpaperBoundaryCallback(repository,service,search);

        executor=Executors.newFixedThreadPool(5);
        wallpaperPagedList= (new LivePagedListBuilder<Integer,Wallpaper>(factory,config))
                .setFetchExecutor(executor)
                .setBoundaryCallback(boundaryCallback)
                .build();

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}