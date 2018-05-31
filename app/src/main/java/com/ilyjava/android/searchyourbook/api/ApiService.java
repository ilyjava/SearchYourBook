package com.ilyjava.android.searchyourbook.api;

import com.ilyjava.android.searchyourbook.model.ItemList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Никита on 28.05.2018.
 */

public interface ApiService {

    @GET("/books/v1/volumes")
    Call<ItemList> getData(@Query("q") String request, @Query("maxResults") int count, @Query("printType") String type);
}
