package com.apkzube.wallfresco.service;

import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.response.WallpaperResponse;
import com.apkzube.wallfresco.util.CommonRestURL;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PexelsService {

    @GET(CommonRestURL.WALLPAPER__URL)
    Call<PelexsResponse> getWallpapers(@Header("Authorization") String key ,
                                       @Query("query") String query,
                                       @Query("per_page") int imgPerPage,
                                       @Query("page") int page,
                                       @Query("orientation") String portrait);

    @GET(CommonRestURL.TRENDING__URL)
    Call<PelexsResponse> getTrendWallpapers(@Query("Authorization") String key ,
                                  @Query("per_page") int imgPerPage,
                                  @Query("page") int page);


}
