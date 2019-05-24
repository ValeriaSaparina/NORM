package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventCategoryIncludeApiResponse;
import com.example.user.myapplication.API.EventsCategoriesApiResponse;
import com.example.user.myapplication.DBHelper;
import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sorts extends Fragment {


    private static final int LENGTH = 22;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        Button btn;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sorts, parent, false));
            text = itemView.findViewById(R.id.category_sort);
            btn = itemView.findViewById(R.id.add_category);


        }


    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        private DatabaseReference myRef = database.getReference();
        private FirebaseAuth mAuth = FirebaseAuth.getInstance();

        List<EventCategoryIncludeApiResponse> categoriesList;
        //Categories cat = new Categories();

        DBHelper dbHelper = new DBHelper(Objects.requireNonNull(getActivity()).getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String uID;
        String STR = "";

        ContentAdapter() {
            uID = mAuth.getUid();

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
//
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

           // categoriesList = cat.getCategoriesList();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//            holder.text.setText(categoriesList.get(position));

            holder.text.setText(categoriesList.get(position).getName());

            View.OnClickListener onClickListener = v -> {
                if (v.getId() == R.id.add_category) {
                    cv.put("name", holder.text.getText().toString());
                    long rowId = db.insert("myTable", null, cv);
                    Cursor c = db.query("myTable", null, null, null, null, null, null);
                    if (c.moveToFirst()) {

                        // определяем номера столбцов по имени в выборке
                        int idColIndex = c.getColumnIndex("id");
                        int nameColIndex = c.getColumnIndex("name");

                        do {
                            // получаем значения по номерам столбцов и пишем все в лог
                            Log.d("API",
                                    "ID = " + c.getInt(idColIndex) +
                                            ", name = " + c.getString(nameColIndex));
//                            STR = "";
                            STR += c.getString(nameColIndex) + ", ";
                            Log.d("API", "STR = " + STR);
                            // переход на следующую строку
                            // а если следующей нет (текущая - последняя), то false - выходим из цикла
                        } while (c.moveToNext());
                        STR = STR.substring(0, STR.length() - 2);
                        CardContentFragment card = new CardContentFragment();
                        CardContentFragment.setCatStr(STR);
                        STR = "";
                        Log.d("API", "STR = " + STR);
                    } else
                        Log.d("API", "0 rows");
                    c.close();
//                    Categories.setMyList(String.valueOf(holder.text.getText()));
                    holder.btn.setText("Удалить");
//                    Log.d("API", "myList: " + Categories.getMyList());
                    //Categories.setSizeMyList(Categories.getMyList().size());
//                    Log.d("API", "sizeList: " + Categories.getMyList().size());
//                    Log.d("API", "sizeList: " + Categories.myList.size());
                    //Categories.sizeMyList = Categories.myList.size();
                    //Log.d("API", "sizeList: " + Categories.sizeMyList);
                }
            };

            holder.btn.setOnClickListener(onClickListener);

        }

        @Override
        public int getItemCount() {
            return Math.toIntExact(LENGTH);
        }
    }


}

