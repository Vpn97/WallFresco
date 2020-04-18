package com.apkzube.wallfresco.service.impl;

import com.apkzube.wallfresco.service.PexelsService;
import com.apkzube.wallfresco.util.CommonRestURL;
import com.apkzube.wallfresco.util.GsonUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PexelsServiceImpl {

    private static Retrofit retrofit = null;

    public static PexelsService getService(){

        if(retrofit==null){

            retrofit=new Retrofit
                    .Builder()
                    .baseUrl(CommonRestURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                    .client(GsonUtil.getOkHttpClientBuilder().build())
                    .build();
        }

        return retrofit.create(PexelsService.class);

    }

}
