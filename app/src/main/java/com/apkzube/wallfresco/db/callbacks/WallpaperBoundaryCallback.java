package com.apkzube.wallfresco.db.callbacks;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

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

/**
 * Wall Fresco
 * Created by Vishal Nagvadiya on 26-04-2020.
 */
public class WallpaperBoundaryCallback extends PagedList.BoundaryCallback<Wallpaper> {

    private WallRepository repository;
    private PexelsService service;
    private String searchString;

    public WallpaperBoundaryCallback(WallRepository repository, PexelsService service, String searchString) {
        this.repository = repository;
        this.service = service;
    }


    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        loadData();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Wallpaper itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        loadData();
    }


    public void loadData() {

        Call<PelexsResponse> call;
        if (searchString == null || TextUtils.isEmpty(searchString)) {
            searchString = CommonRestURL.getRandomSearch();
        }
        call = service.getWallpapers(CommonRestURL.getApiKEY(),
                searchString,
                CommonRestURL.PER_PAGE_WALLPAPER,
                CommonRestURL.getRandomPage(), "portrait");

        call.enqueue(new Callback<PelexsResponse>() {
            @Override
            public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                Log.d(Constant.TAG, "onResponse: " + new Gson().toJson(response.body()));

                if (null != response && null != response.body()) {
                    ArrayList<Wallpaper> wallpapers = ConverterUtil.setWallpaperCategory(ConverterUtil.convertResponseToEntityList(response.body()), searchString);
                    repository.insertAllWallpapers(wallpapers);
                }
            }

            @Override
            public void onFailure(Call<PelexsResponse> call, Throwable t) {
                Log.d(Constant.TAG, "onFailure: " + new Gson().toJson(t));
                loadData();
            }
        });
    }
}
