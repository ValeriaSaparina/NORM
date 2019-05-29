package com.example.user.myapplication.design;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventCategoryIncludeApiResponse;
import com.example.user.myapplication.API.EventsCategoriesApiResponse;
import com.example.user.myapplication.DBHelper;
import com.example.user.myapplication.R;
import com.example.user.myapplication.pages.SignIn;
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

public class Sorts extends Fragment {


    private static final int LENGTH = 22;
    Events events;

    static String[] dates;
    static String[] categories;
    static String[] names;
    static String[] links;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onSaveInstanceState(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        SignIn.initCategories();
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }






    @Override
    public void onDestroy() {
        super.onDestroy();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int clearCount = db.delete("myTable", null, null);
        Log.d("API", "deleted rows count = " + clearCount);
    }


    List<EventCategoryIncludeApiResponse> categoriesList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();


        List<Integer> categoryId = new ArrayList<>();
        List<String> categoryName = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        Call<EventsCategoriesApiResponse> call;
        call = api.categoriesList();

        call.enqueue(new Callback<EventsCategoriesApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventsCategoriesApiResponse> call, @NonNull Response<EventsCategoriesApiResponse> response) {
                try {
                    EventsCategoriesApiResponse eventsCategoriesApiResponse = response.body();

                    Log.d("API", "raw response: " + response.raw().toString());
                    if (eventsCategoriesApiResponse == null) Log.d("API", "response is null");
                    else {categoriesList = eventsCategoriesApiResponse.getValues();
                        Log.d("API", "success");
                        Log.d("API", "categoryID: " + categoriesList.get(0).getId());
                        Log.d("API", "categoryList size: " + eventsCategoriesApiResponse.getValues().size());

                        for (int i = 0; i < categoriesList.size(); i++) {
                            categoryName.add(categoriesList.get(i).getName());
                            categoryId.add(categoriesList.get(i).getId());
                            Log.d("API", "catNAme: " + categoryName.get(i));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ContentAdapter.self.setNamesAndIds(categoryName, categoryId);

            }

            @Override
            public void onFailure (@NonNull Call <EventsCategoriesApiResponse> call, @NonNull Throwable t){
                Log.d("API", "failed");
            }
        });
    }

}

