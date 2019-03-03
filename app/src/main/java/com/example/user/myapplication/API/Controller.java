package com.example.user.myapplication.API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


    public class Controller implements Callback<List<events>> {

        static final String BASE_URL = "https://api.timepad.ru/v1/";

        public void start() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            API api = retrofit.create(API.class);

            Call<List<events>> call = api.eventList(3);
            call.enqueue(this);



            Log.d("API", "START");

        }

        @Override
        public void onResponse(Call<List<events>> call, Response<List<events>> response) {
            if(response.isSuccessful()) {
                List<events> eventsList = response.body();
                eventsList.forEach(event -> Log.d("API", event.name));
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<List<events>> call, Throwable t) {
            t.printStackTrace();
        }
    }


