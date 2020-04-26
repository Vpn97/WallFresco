package com.apkzube.wallfresco.ui.trending;

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

public class TrendingViewModel extends AndroidViewModel {

    private Application application;
    private MutableLiveData<List<Wallpaper>> wallpaperLivedata =new MutableLiveData<>();
    private WallRepository repository;

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
        this.repository=new WallRepository(application);
    }

    public LiveData<List<Wallpaper>> getTrendingWallpaper(){
        return repository.getTrendWallpapers();
    }


    public void getTrendingWallpaperWeb() {
        PexelsService service= PexelsServiceImpl.getService();
        service.getTrendWallpapers(CommonRestURL.getApiKEY(),CommonRestURL.PER_PAGE_TRENDING,1)
                .enqueue(new Callback<PelexsResponse>() {
                    @Override
                    public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                        Log.d(Constant.TAG, "onResponse: getTrendingWallpaper"+new Gson().toJson(response.body()));

                        if(null!=response && null!=response.body()) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.
                                    setTrendWallpaper(ConverterUtil.convertResponseToEntityList(response.body()));
                           repository.insertAllWallpapers(wallpapers);
                        }
                    }
                    @Override
                    public void onFailure(Call<PelexsResponse> call, Throwable t) {
                        Log.d(Constant.TAG, "onFailure: getTrendingWallpaper"+new Gson().toJson(t));
                    }
                });
    }

}