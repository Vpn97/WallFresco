package com.apkzube.wallfresco.db.datasource;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.service.PexelsService;
import com.apkzube.wallfresco.util.CommonRestURL;
import com.apkzube.wallfresco.util.Constant;
import com.apkzube.wallfresco.util.ConverterUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperDataSource extends PageKeyedDataSource<Integer, Wallpaper> {

    private PexelsService service;
    private Application application;
    private WallRepository repository;

    public WallpaperDataSource(PexelsService service, Application application) {
        this.service = service;
        this.application = application;
        this.repository = new WallRepository(application);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Wallpaper> callback) {

        service.getWallpapers(CommonRestURL.getApiKEY(), "Random Wallpaper", CommonRestURL.PER_PAGE_WALLPAPER, 1, "portrait")
                .enqueue(new Callback<PelexsResponse>() {
                    @Override
                    public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                        Log.d(Constant.TAG, "onResponse: " + new Gson().toJson(response.body()));

                        if (null != response && null != response.body()) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.convertResponseToEntityList(response.body());
                            repository.insertAllWallpapers(wallpapers);
                            callback.onResult(wallpapers, null, 2);
                        }

                    }

                    @Override
                    public void onFailure(Call<PelexsResponse> call, Throwable t) {
                        Log.d(Constant.TAG, "onFailure: " + new Gson().toJson(t));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Wallpaper> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Wallpaper> callback) {

        service.getWallpapers(CommonRestURL.getApiKEY(), "Random Wallpaper", CommonRestURL.PER_PAGE_WALLPAPER, params.key, "portrait")
                .enqueue(new Callback<PelexsResponse>() {
                    @Override
                    public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                        Log.d(Constant.TAG, "onResponse: " + new Gson().toJson(response.body()));

                        if (null != response && null != response.body()) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.convertResponseToEntityList(response.body());
                            repository.insertAllWallpapers(wallpapers);
                            callback.onResult(wallpapers, params.key + 1);
                        }

                    }

                    @Override
                    public void onFailure(Call<PelexsResponse> call, Throwable t) {
                        Log.d(Constant.TAG, "onFailure: " + new Gson().toJson(t));
                    }
                });
    }
}
