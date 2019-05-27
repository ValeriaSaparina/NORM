package com.example.user.myapplication.API;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("/v1/dictionary/event_categories.json?")
    Call<EventsCategoriesApiResponse> categoreisList();

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit);

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit, @Query("cities") List<String> cities);

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit, @Query("cities") List<String> cities, @Query("category_ids") String categories);

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit, @Query("skip") int skip);

    @GET("v1/events.json")
    Call<EventsResponse> eventList(@Query("limit") int limit, @Query("skip") int skip, @Query("cities") List<String> cities);

}
