package com.example.user.myapplication.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("events.json?")
    Call<List<events>> eventList(@Query("limit") int limit);

    @GET("events.json?")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("category") int idCategory);

    @GET("events.json?")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("cities") String city);

    @GET("events.json")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("skip") int skip,
                                 @Query("category") int idCategory, @Query("cities") String city);


}
