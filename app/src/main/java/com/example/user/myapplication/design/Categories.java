package com.example.user.myapplication.design;

import com.example.user.myapplication.API.EventCategoryIncludeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class Categories {
    private List<EventCategoryIncludeApiResponse> categoriesList;
    private List<String> myCategoriesList;
    private List<String> myIdList;

    private List<Integer> categoryId;
    private List<String> categoryName;


    public List<String> getMyCategoriesList() {
        return myCategoriesList;
    }

    public List<String> getMyIdList() {
        return myIdList;
    }


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
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.timepad.ru/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        API api = retrofit.create(API.class);
//        Call<EventsCategoriesApiResponse> call;
//        call = api.categoreisList();
//
//        call.enqueue(new Callback<EventsCategoriesApiResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<EventsCategoriesApiResponse> call, @NonNull Response<EventsCategoriesApiResponse> response) {
//                try {
//                    EventsCategoriesApiResponse eventsCategoriesApiResponse = response.body();
//
//                    Log.d("API", "raw response: " + response.raw().toString());
//                    if (eventsCategoriesApiResponse == null) Log.d("API", "response is null");
//                    else {categoriesList = eventsCategoriesApiResponse.getValue();
//                        Log.d("API", "success");
//
//                        Log.d("API", "categoryID: " + categoriesList.get(0).getId());
//                        Log.d("API", "categoryList size: " + eventsCategoriesApiResponse.getValue().size());
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
//                    }
//                Log.d("API", "AAAAAAAAAAAAAAAAAAAAAAAAAAaa");
//                } catch (Exception e) {
//                    e.printStackTrace();}
//
//
//                }
//
//                @Override
//                public void onFailure (@NonNull Call < EventsCategoriesApiResponse > call, @NonNull Throwable t){
//                    Log.d("API", "failed");
//                }
//            });


        myCategoriesList = new ArrayList<>();
        myIdList = new ArrayList<>();

        myCategoriesList.add("Наука");
        myCategoriesList.add("Бизнес");
        myCategoriesList.add("ИТ и интернет");
        myCategoriesList.add("Иностранные языки");
        myCategoriesList.add("Выставки");
        myCategoriesList.add("Концерты");
        myCategoriesList.add("Театры");
        myCategoriesList.add("Вечеринки");
        myCategoriesList.add("Экскурсии и путешествия");
        myCategoriesList.add("Еда");
        myCategoriesList.add("Красота и здоровье");
        myCategoriesList.add("Для детей");
        myCategoriesList.add("Психология и самопознание");
        myCategoriesList.add("Кино");
        myCategoriesList.add("Хобби и творчество");
        myCategoriesList.add("Искусство и культура");
        myCategoriesList.add("Другие развлечения");
        myCategoriesList.add("Спорт");
        myCategoriesList.add("Образование за рубежом");
        myCategoriesList.add("Гражданские проекты");
        myCategoriesList.add("Интеллектуальные игры");
        myCategoriesList.add("Другие события");


        myIdList.add("2465");
        myIdList.add("217");
        myIdList.add("452");
        myIdList.add("382");
        myIdList.add("458");
        myIdList.add("460");
        myIdList.add("459");
        myIdList.add("457");
        myIdList.add("461");
        myIdList.add("456");
        myIdList.add("399");
        myIdList.add("379");
        myIdList.add("453");
        myIdList.add("374");
        myIdList.add("524");
        myIdList.add("525");
        myIdList.add("463");
        myIdList.add("376");
        myIdList.add("1315");
        myIdList.add("1940");
        myIdList.add("2335");
        myIdList.add("462");

    }

}
