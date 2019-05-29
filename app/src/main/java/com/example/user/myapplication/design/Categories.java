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

import static com.example.user.myapplication.design.CardContentFragment.BASE_URL;

public class Categories {
    private List<EventCategoryIncludeApiResponse> categoriesList;

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
        categoryId = new ArrayList<>();
        categoryName = new ArrayList<>();

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

//                        for (EventCategoryIncludeApiResponse er : categoriesList) {
//                            Log.d("API", "Categories id = " + er.getId() + " name = " + er.getName());
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
//                        }
//                        Log.d("API", "Categories: " + categoriesList);
                    }
                    } catch (Exception e) {
                    e.printStackTrace();}


                }

                @Override
                public void onFailure (@NonNull Call <EventsCategoriesApiResponse> call, @NonNull Throwable t){
                    Log.d("API", "failed");
                }
            });



      /*  categoryName.add("Наука");
        categoryName.add("Бизнес");
        categoryName.add("ИТ и интернет");
        categoryName.add("Иностранные языки");
        categoryName.add("Выставки");
        categoryName.add("Концерты");
        categoryName.add("Театры");
        categoryName.add("Вечеринки");
        categoryName.add("Экскурсии и путешествия");
        categoryName.add("Еда");
        categoryName.add("Красота и здоровье");
        categoryName.add("Для детей");
        categoryName.add("Психология и самопознание");
        categoryName.add("Кино");
        categoryName.add("Хобби и творчество");
        categoryName.add("Искусство и культура");
        categoryName.add("Другие развлечения");
        categoryName.add("Спорт");
        categoryName.add("Образование за рубежом");
        categoryName.add("Гражданские проекты");
        categoryName.add("Интеллектуальные игры");
        categoryName.add("Другие события");


        categoryId.add(2465);
        categoryId.add(217);
        categoryId.add(452);
        categoryId.add(382);
        categoryId.add(458);
        categoryId.add(460);
        categoryId.add(459);
        categoryId.add(457);
        categoryId.add(461);
        categoryId.add(456);
        categoryId.add(399);
        categoryId.add(379);
        categoryId.add(453);
        categoryId.add(374);
        categoryId.add(524);
        categoryId.add(525);
        categoryId.add(463);
        categoryId.add(376);
        categoryId.add(131);
        categoryId.add(194);
        categoryId.add(233);
        categoryId.add(462);
*/


    }

}
