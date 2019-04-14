package com.example.user.myapplication.API;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit);

//    @GET("events.json?")
//    Call<events> eventList(@Query("limit") int limit);

   /* @GET("events.json?")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("category") int idCategory);

    @GET("events.json?")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("cities") String city);

    @GET("events.json")
    Call<List<events>> eventList(@Query("limit") int limit, @Query("skip") int skip,
                                 @Query("category") int idCategory, @Query("cities") String city);*/


}
