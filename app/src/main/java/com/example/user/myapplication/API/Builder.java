package com.example.user.myapplication.API;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.user.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Builder extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Log.d("AAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAA");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.timepad.ru/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<List<events>> call = api.eventList(3); //СЧИТЫВАЕТ СОБЫТИЯ (3ШТ)
        call.enqueue(new Callback<List<events>>() {
            @Override
            public void onResponse(Call<List<events>> call, Response<List<events>> response) {
                Log.d("API", "Events" + call);
            }

            @Override
            public void onFailure(Call<List<events>> call, Throwable t) {
                Log.d("API", "error", t);
            }
        });
    }

}
