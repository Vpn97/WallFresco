package com.apkzube.wallfresco.service;

import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.response.WallpaperResponse;
import com.apkzube.wallfresco.util.CommonRestURL;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PexelsService {

    @POST(CommonRestURL.WALLPAPER__URL)
    Call<PelexsResponse> getWallpapers(@Query("Authorization") String key ,
                                       @Query("query") String query,
                                       @Query("per_page") int imgPerPage,
                                       @Query("page") int page);

    @POST(CommonRestURL.TRENDING__URL)
    Call<PelexsResponse> getTrendWallpapers(@Query("Authorization") String key ,
                                  @Query("per_page") int imgPerPage,
                                  @Query("page") int page);


}
