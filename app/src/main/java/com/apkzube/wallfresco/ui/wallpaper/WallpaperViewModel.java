package com.apkzube.wallfresco.ui.wallpaper;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperViewModel extends AndroidViewModel{

    private Application application;
    private LiveData<List<Wallpaper>> wallpaperLiveData=new MutableLiveData<>();
    private WallRepository repository;

    public WallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        repository=new WallRepository(application);
    }



    public LiveData<List<Wallpaper>> getAllWallpaper(){
        wallpaperLiveData= repository.getAllWallpaper();
        return wallpaperLiveData;
    }


    public void setWallpapers(){
        PexelsService service= PexelsServiceImpl.getService();

        service.getWallpapers(CommonRestURL.getApiKEY(),"Wallpaper",CommonRestURL.PER_PAGE_WALLPAPER,1)
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
}