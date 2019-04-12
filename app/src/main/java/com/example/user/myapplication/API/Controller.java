package com.example.user.myapplication.API;

import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


    public class Controller  {




        static final String BASE_URL = "https://api.timepad.ru/v1/";

//        public void getReport() {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//
//            API api = retrofit.create(API.class);
//
//            call = api.eventList(1);
//            call.enqueue(Callback<events>() {
//                @Override
//                public void onResponse (Call<List<events>> call, Response < List < events >> response){
//                    if (response.isSuccessful()) {
//                        String text = response.body().toString();
//                        textView.setText(text);
//                        //eventsList.forEach(event -> Log.d("API", event.name));
//                    } else {
//                        System.out.println(response.errorBody());
//                    }
//                }
//                @Override
//                public void onFailure(Call<events> call, Throwable t) {
//                }
//            });
//
//
//
//
//            Log.d("API", "START");
//        }

//        void getReport() {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            API api = retrofit.create(API.class);
//            Call<List<events>> call = api.eventList(1);
//            call.enqueue(new Callback<events>() {
//                @Override
//                public void onResponse(Call<events> call, Response<events> response) {
//                    try {
//                        String textWord = response.body().toString();
//                        textView.setText(textWord);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<events> call, Throwable t) {
//
//                }
//            });
//        }
//
//        @Override
//        public void onResponse(Call<List<events>> call, Response<List<events>> response) {
//
//        }
//
//        @Override
//        public void onFailure(Call<List<events>> call, Throwable t) {
//
//        }
    }


