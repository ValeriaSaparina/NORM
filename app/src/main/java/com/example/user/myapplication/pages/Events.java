package com.example.user.myapplication.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.events;
import com.example.user.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Events extends AppCompatActivity {

   // TextView textView;
    static final String BASE_URL = "https://api.timepad.ru/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getReport();

    }

    void getReport() {
        Log.d("API", "Start");
        Gson gson = new GsonBuilder()

                    .setLenient()
                    .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        Call<events> call = api.eventList(3);
        call.enqueue(new Callback<events>() {
            @Override
            public void onResponse(Call<events> call, Response<events> response) {
                try {
                    String textWord = response.body().getCity();
                    TextView textView = findViewById(R.id.RESTAPI);
                    textView.setText(textWord);
                    Log.d("API", textWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<events> call, Throwable t) {
                Log.d("API", "failed");
            }
        });
    }
}
