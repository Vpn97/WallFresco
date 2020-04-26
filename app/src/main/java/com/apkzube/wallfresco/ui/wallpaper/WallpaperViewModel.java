package com.apkzube.wallfresco.ui.wallpaper;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.apkzube.wallfresco.db.datasource.WallpaperDataSource;
import com.apkzube.wallfresco.db.datasource.WallpaperDataSourceFactory;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.service.PexelsService;
import com.apkzube.wallfresco.service.impl.PexelsServiceImpl;
import com.apkzube.wallfresco.util.CommonRestURL;
import com.apkzube.wallfresco.util.Constant;
import com.apkzube.wallfresco.util.ConverterUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperViewModel extends AndroidViewModel{

    private Application application;
    private WallRepository repository;
    private LiveData<List<Wallpaper>> wallpaperLiveData=new MutableLiveData<>();
    private LiveData<List<Wallpaper>> wallpaperCategoryLiveData=new MutableLiveData<>();
    private Executor executor;
    private LiveData<PagedList<Wallpaper>> wallpaperPagedList;

    private LiveData<WallpaperDataSource> wallDSLiveData;

    public WallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository=new WallRepository(application);
        PexelsService service= PexelsServiceImpl.getService();
        WallpaperDataSourceFactory factory=new WallpaperDataSourceFactory(service,application);
        wallDSLiveData=factory.getSourceMutableLiveData();

        PagedList.Config config=(new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPageSize(CommonRestURL.PER_PAGE_WALLPAPER)
                .setPrefetchDistance(4)
                .build();

        executor=Executors.newFixedThreadPool(5);
        wallpaperPagedList= (new LivePagedListBuilder<Integer,Wallpaper>(factory,config))
                .setFetchExecutor(executor)
                .build();

    }



    public LiveData<List<Wallpaper>> getAllWallpaper(){
        wallpaperLiveData=repository.getPortraitWallpapers();
        return wallpaperLiveData;
    }


    public void getWallpaperWeb(){
        PexelsService service= PexelsServiceImpl.getService();

        service.getWallpapers(CommonRestURL.getApiKEY(),"Random Wallpaper",CommonRestURL.PER_PAGE_WALLPAPER,10,"portrait")
                .enqueue(new Callback<PelexsResponse>() {
            @Override
            public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                Log.d(Constant.TAG, "onResponse: "+new Gson().toJson(response.body()));

                if(null!=response && null!=response.body()) {
                    ArrayList<Wallpaper> wallpapers = ConverterUtil.convertResponseToEntityList(response.body());
                    repository.insertAllWallpapers(wallpapers);
                }
            }

            @Override
            public void onFailure(Call<PelexsResponse> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: "+new Gson().toJson(t));
            }
        });

    }

    public LiveData<List<Wallpaper>> getCategoryWallpaper(String category){
        wallpaperCategoryLiveData= repository.getCategoryWallpapers(category);
        return wallpaperLiveData;
    }
    public void getCategoryWallpaperWeb(String category,int page){
        PexelsService service= PexelsServiceImpl.getService();

        service.getWallpapers(CommonRestURL.getApiKEY(),category+" Wallpaper",CommonRestURL.PER_PAGE_CATEGORY,page,"portrait")
                .enqueue(new Callback<PelexsResponse>() {
                    @Override
                    public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                        Log.d(Constant.TAG, "onResponse: "+new Gson().toJson(response.body()));

                        if(null!=response && null!=response.body()) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.
                                    setWallpaperCategory(ConverterUtil.convertResponseToEntityList(response.body()),category);
                            repository.insertAllWallpapers(wallpapers);
                        }
                    }
                    @Override
                    public void onFailure(Call<PelexsResponse> call, Throwable t) {
                        Log.d(Constant.TAG, "onFailure: "+new Gson().toJson(t));
                    }
                });
    }



    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LiveData<List<Wallpaper>> getWallpaperLiveData() {
        return wallpaperLiveData;
    }

    public void setWallpaperLiveData(LiveData<List<Wallpaper>> wallpaperLiveData) {
        this.wallpaperLiveData = wallpaperLiveData;
    }

    public LiveData<List<Wallpaper>> getWallpaperCategoryLiveData() {
        return wallpaperCategoryLiveData;
    }

    public void setWallpaperCategoryLiveData(LiveData<List<Wallpaper>> wallpaperCategoryLiveData) {
        this.wallpaperCategoryLiveData = wallpaperCategoryLiveData;
    }

    public LiveData<PagedList<Wallpaper>> getWallpaperPagedList() {
        return wallpaperPagedList;
    }

    public void setWallpaperPagedList(LiveData<PagedList<Wallpaper>> wallpaperPagedList) {
        this.wallpaperPagedList = wallpaperPagedList;
    }
}