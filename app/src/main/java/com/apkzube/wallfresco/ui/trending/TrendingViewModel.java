package com.apkzube.wallfresco.ui.trending;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apkzube.wallfresco.db.entity.Wallpaper;
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

    public TrendingViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }

    public LiveData<List<Wallpaper>> getTrendingWallpaper() {
        PexelsService service= PexelsServiceImpl.getService();
        service.getTrendWallpapers(CommonRestURL.getApiKEY(),CommonRestURL.PER_PAGE_WALLPAPER,1)
                .enqueue(new Callback<PelexsResponse>() {
                    @Override
                    public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                        Log.d(Constant.TAG, "onResponse: "+new Gson().toJson(response.body()));

                        if(null!=response && null!=response.body()) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.convertResponseToEntityList(response.body());
                            wallpaperLivedata.setValue(wallpapers);
                        }
                    }

                    @Override
                    public void onFailure(Call<PelexsResponse> call, Throwable t) {
                        Log.d(Constant.TAG, "onFailure: "+new Gson().toJson(t));
                    }
                });
        return wallpaperLivedata;
    }




}