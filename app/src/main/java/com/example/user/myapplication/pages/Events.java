package com.example.user.myapplication.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.API.EventsResponse;
import com.example.user.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Events extends AppCompatActivity {

    static final String BASE_URL = "https://api.timepad.ru/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getReport();

    }

    void getReport() {
        Log.d("API", "start");
        Gson gson = new GsonBuilder()

                    .setLenient()
                    .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        Call<EventsResponse> call = api.eventList(3);
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                try {
                    EventsResponse eventsResponse = response.body();
                    Log.d("API", "raw response: " + response.raw().toString());
                    if (eventsResponse == null) Log.d("API", "response is null");
                    else {
                        List<EventResponse> myList = eventsResponse.getValues();
                        for(EventResponse er: myList) Log.d("API", "id = " + er.getId() + " name = " + er.getName());
                        Log.d("API", "event list is " + eventsResponse.getTotal() + " length");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                Log.d("API", "failed");
            }
        });
    }
}
