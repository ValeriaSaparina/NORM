package com.example.user.myapplication.design;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.API.EventsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.myapplication.design.CardContentFragment.BASE_URL;

public class Events {

    private List<EventResponse> myList;
    private String[] names;
    private String[] dates;
    private String[] categories;
    private String[] links;

    public Events(int LENGTH, String categoriesStr) {
        names = new String[LENGTH];
        categories = new String[LENGTH];
        dates = new String[LENGTH];
        links = new String[LENGTH];

        Log.d("API", "start");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        List<String> cityList = new ArrayList<>();

        cityList.add("Казань");
        Call<EventsResponse> call;

        Log.d("API", "categoryStr: " + categoriesStr);
        if (!(categoriesStr.equals(""))) {
            call = api.eventList(LENGTH, cityList, categoriesStr);
        } else call = api.eventList(LENGTH, cityList);

        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {
                try {
                    EventsResponse eventsResponse = response.body();

                    Log.d("API", "raw response: " + response.raw().toString());
                    if (eventsResponse == null) Log.d("API", "response is null");
                    else {
                        myList = eventsResponse.getValues();
                        for (EventResponse er : myList) {
                            Log.d("API", "id = " + er.getId() + " name = " + er.getName() + " url = " + er.getUrl() + " img = " + er.getPoster_image().getDefault_url());
                        }
                        Log.d("API", "event list is " + eventsResponse.getTotal() + " length");
                        for (int i = 0; i < LENGTH; i++) {
                            final String[] str = {""};
                            char[] dst=new char[10];
                            names[i] = myList.get(i).getName();
                            categories[i] = myList.get(i).getCategories().get(0).getName();
                            links[i] = myList.get(i).getUrl();
                            myList.get(i).getStarts_at().getChars(0, 10, dst, 0);
                            for(char c : dst) str[0] += c;
                            dates[i] = str[0];
                            Log.d("API", "STR: " + dates[i]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<EventsResponse> call, @NonNull Throwable t) {
                Log.d("API", "failed");
            }
        });
    }

    public String[] getNames() {
        Log.d("API", "nameEvents: " + names[0]);
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String[] getDates() {
        Log.d("API", "dateEvents: " + dates[0]);
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public String[] getCategories() {
        Log.d("API", "catEvents: " + categories[0]);
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }
}
