package com.ilyjava.android.searchyourbook.api;

import com.ilyjava.android.searchyourbook.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Никита on 28.05.2018.
 */

public class RetroClient {
    public static final String ROOT_URL = "https://www.googleapis.com";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    /**
     * Get API Service
     *
     * @return API Service
     */
    public static ApiService getApiService(){
        return getRetrofitInstance().create(ApiService.class);
    }
}
