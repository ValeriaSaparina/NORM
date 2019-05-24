package com.example.user.myapplication.design;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventCategoryIncludeApiResponse;
import com.example.user.myapplication.API.EventsCategoriesApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Categories {
    private List<EventCategoryIncludeApiResponse> categoriesList;
    static List<String> myList;

    private List<Integer> categoryId;
    private List<String> categoryName;

    public List<Integer> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Integer> categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(List<String> categoryName) {
        this.categoryName = categoryName;
    }




    public Categories() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.timepad.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        API api = retrofit.create(API.class);
        Call<EventsCategoriesApiResponse> call;
        call = api.categoreisList();

        call.enqueue(new Callback<EventsCategoriesApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventsCategoriesApiResponse> call, @NonNull Response<EventsCategoriesApiResponse> response) {
                try {
                    EventsCategoriesApiResponse eventsCategoriesApiResponse = response.body();

                    Log.d("API", "raw response: " + response.raw().toString());
                    if (eventsCategoriesApiResponse == null) Log.d("API", "response is null");
                    else {categoriesList = eventsCategoriesApiResponse.getValue();
                        Log.d("API", "success");

                        Log.d("API", "categoryID: " + categoriesList.get(0).getId());
                        Log.d("API", "categoryList size: " + eventsCategoriesApiResponse.getValue().size());
                        for (EventCategoryIncludeApiResponse er : categoriesList) {
                            Log.d("API", "Categories id = " + er.getId() + " name = " + er.getName());
//                            for (int i = 0; i < LENGTH; i++) {
//                                final String[] str = {""};
//                                char[] dst = new char[10];
//                                names[i] = myList.get(i).getName();
//                                categories[i] = myList.get(i).getCategories().get(0).getName();
//                                links[i] = myList.get(i).getUrl();
//                                myList.get(i).getStarts_at().getChars(0, 10, dst, 0);
//                                for (char c : dst) str[0] += c;
//                                dates[i] = str[0];
//                                Log.d("API", "STR: " + dates[i]);
//                            }
                        }
                        Log.d("API", "Categories: " + categoriesList);
                    }
                Log.d("API", "AAAAAAAAAAAAAAAAAAAAAAAAAAaa");
                } catch (Exception e) {
                    e.printStackTrace();}


                }

                @Override
                public void onFailure (@NonNull Call < EventsCategoriesApiResponse > call, @NonNull Throwable t){
                    Log.d("API", "failed");
                }
            });

//        categoriesList = new ArrayList<>();
        myList = new ArrayList<>();
//
//        categoriesList.add("Наука 2465");
//        categoriesList.add("Бизнес");
//        categoriesList.add("ИТ и интернет");
//        categoriesList.add("Иностранные языки");
//        categoriesList.add("Выставки");
//        categoriesList.add("Концерты 460");
//        categoriesList.add("Театры 459");
//        categoriesList.add("Вечеринки");
//        categoriesList.add("Экскурсии и путешествия");
//        categoriesList.add("Еда");
//        categoriesList.add("Красота и здоровье 399");
//        categoriesList.add("Для детей 379");
//        categoriesList.add("Психология и самопознание 453");
//        categoriesList.add("Кино");
//        categoriesList.add("Хобби и творчество");
//        categoriesList.add("Искусство и культура");
//        categoriesList.add("Другие развлечения");
//        categoriesList.add("Спорт");
//        categoriesList.add("Образование за рубежом");
//        categoriesList.add("Гражданские проекты");
//        categoriesList.add("Интеллектуальные события");
//        categoriesList.add("Другие события");
    }



    public static List<String> getMyList() {
        return myList;
    }

    public static void setMyList(String str) {
        myList.add(str);
    }

}
