package com.example.user.myapplication.API;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Report {
    private List<EventResponse> myList;
    static final String BASE_URL = "https://api.timepad.ru/";
    private static final int LENGTH = 18;

    public List<EventResponse> getMyList() {
   //     getReport();
        return myList;
    }



//
//            @Override
//            public void onResponse(@NonNull Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {
//                try {
//                    EventsResponse eventsResponse = response.body();
//
//                    Log.d("API", "raw response: " + response.raw().toString());
//                    if (eventsResponse == null) Log.d("API", "response is null");
//                    else {
//                        List<EventResponse> List = eventsResponse.getValues();
//                        myList = List;
//                        for (EventResponse er : List) {
//                            Log.d("API", "id = " + er.getId() + " name = " + er.getName() + " url = " + er.getUrl() + " img = " + er.getPoster_image().getDefault_url());
//                        }
//                        Log.d("API", "event list is " + eventsResponse.getTotal() + " length");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<EventsResponse> call, @NonNull Throwable t) {
//                Log.d("API", "failed");
//            }
//        });
//    }
}
