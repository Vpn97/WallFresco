package com.apkzube.wallfresco.db.callbacks;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;
import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.service.PexelsService;
import com.apkzube.wallfresco.util.CommonRestURL;
import com.apkzube.wallfresco.util.Constant;
import com.apkzube.wallfresco.util.ConverterUtil;
import com.apkzube.wallfresco.util.DataStorage;
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
    private DataStorage storage;

    public WallpaperBoundaryCallback(WallRepository repository, PexelsService service, String searchString, Context context) {
        this.repository = repository;
        this.service = service;
        this.searchString = searchString;
        this.storage=new DataStorage(context,context.getResources().getString(R.string.user_data));
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

        //for null string search
        if (searchString == null || TextUtils.isEmpty(searchString)) {
            call = service.getWallpapers(CommonRestURL.getApiKEY(),
                    CommonRestURL.getRandomSearch(),
                    CommonRestURL.PER_PAGE_WALLPAPER,
                    CommonRestURL.getRandomPage(), "portrait");

            call.enqueue(new Callback<PelexsResponse>() {
                @Override
                public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                  //  Log.d(Constant.TAG, "onResponse: " + new Gson().toJson(response.body()));
                    ArrayList<Wallpaper> wallpapers;
                    if (null != response && null != response.body()) {
                        if (null != response.body().getWallpapers() && response.body().getWallpapers().size() > 0) {
                            wallpapers = ConverterUtil.convertResponseToEntityList(response.body());
                            repository.insertAllWallpapers(wallpapers);
                            searchString = "";
                        } else {
                            loadData();
                        }
                    } else {
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<PelexsResponse> call, Throwable t) {
                    Log.d(Constant.TAG, "onFailure: " + new Gson().toJson(t));
                    loadData();
                }
            });

        } else {


            // for category search

            int nextPage= (int)storage.read(searchString+"_next_page", DataStorage.INTEGER);
            if(nextPage!=0 && nextPage>0){
                call = service.getWallpapers(CommonRestURL.getApiKEY(),
                        searchString,
                        CommonRestURL.PER_PAGE_WALLPAPER,
                        nextPage, "portrait");
            }else{
                call = service.getWallpapers(CommonRestURL.getApiKEY(),
                        searchString,
                        CommonRestURL.PER_PAGE_WALLPAPER,
                        1, "portrait");
            }


            call.enqueue(new Callback<PelexsResponse>() {
                @Override
                public void onResponse(Call<PelexsResponse> call, Response<PelexsResponse> response) {
                    // Log.d(Constant.TAG, "onResponse: " + new Gson().toJson(response.body()));

                    if (null != response && null != response.body()) {

                        if (null != response.body().getWallpapers() && response.body().getWallpapers().size() > 0) {
                            ArrayList<Wallpaper> wallpapers = ConverterUtil.setWallpaperCategory(ConverterUtil.convertResponseToEntityList(response.body()), searchString);
                            repository.insertAllWallpapers(wallpapers);

                            if(response.body().getNextPageUrl()!=null && !response.body().getNextPageUrl().equalsIgnoreCase("")){
                                storage.write(searchString+"_next_page",response.body().getPage()+1);
                            }
                        } else {
                            if(response.body().getNextPageUrl()!=null && !response.body().getNextPageUrl().equalsIgnoreCase("")) {
                                loadData();
                            }
                        }
                    } else {
                        loadData();
                    }
                }

                @Override
                public void onFailure(Call<PelexsResponse> call, Throwable t) {
                    Log.d(Constant.TAG, "onFailure: " + new Gson().toJson(t));
                    //loadData();
                }
            });

        }


    }
}
