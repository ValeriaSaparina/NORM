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
import android.widget.Toast;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventCategoryIncludeApiResponse;
import com.example.user.myapplication.API.EventsCategoriesApiResponse;
import com.example.user.myapplication.DBHelper;
import com.example.user.myapplication.R;
import com.example.user.myapplication.pages.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.myapplication.design.CardContentFragment.BASE_URL;

public class Sorts extends Fragment {


    private static final int LENGTH = 22;
    Events events;
    ContentAdapter ca;

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
        ca = adapter;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        Button btn;

        TextView cardName;
        TextView cardDate;
        TextView cardCat;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sorts, parent, false));
            text = itemView.findViewById(R.id.category_sort);
            btn = itemView.findViewById(R.id.add_category);

            cardName = itemView.findViewById(R.id.card_title);
            cardDate = itemView.findViewById(R.id.card_date_text);
            cardCat = itemView.findViewById(R.id.card_category_text);

        }


    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        private DatabaseReference myRef = database.getReference();
        private FirebaseAuth mAuth = FirebaseAuth.getInstance();

        List<String> myCategoriesList;
        List<Integer> myIdList;

        DBHelper dbHelper = new DBHelper(Objects.requireNonNull(getActivity()).getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String uID;
        String STR = "";
        String STR2 = "";

       void setNamesAndIds(List<String> names, List<Integer> ids) {
           this.myCategoriesList = names;
           this.myIdList = ids;
           notifyDataSetChanged();
       }



        ContentAdapter() {
            uID = mAuth.getUid();
            myCategoriesList = new ArrayList<>();
            myCategoriesList.add("");
            myIdList = new ArrayList<>();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }


        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Log.d("API", "bind position: " + position);
            Log.d("API", "categories size: "+ (myCategoriesList == null ? "null" : String.valueOf(myCategoriesList.size())));
            if(myCategoriesList == null) holder.text.setText("");
            else {
                int listSize = myCategoriesList.size();
                holder.text.setText(listSize == 1 ? myCategoriesList.get(listSize - 1) : myCategoriesList.get(position));
            }

            AtomicBoolean flag = new AtomicBoolean(true);
            final Cursor[] c = new Cursor[1];

            View.OnClickListener onClickListener = v -> {
                if (flag.get()) {
                    Log.d("API", "--- Insert in mytable: ---");
                    cv.put("name", holder.text.getText().toString());
                    cv.put("idCat", myIdList.get(position));
                    long rowID = db.insert("mytable", null, cv);
                    Log.d("API", "row inserted, ID = " + rowID);
                    Log.d("API", "--- Rows in mytable: ---");
                    c[0] = db.query("myTable", null, null, null, null, null, null);
                    if (c[0].moveToFirst()) {

                        int idColIndex = c[0].getColumnIndex("id");
                        int nameColIndex = c[0].getColumnIndex("name");
                        int idCatColIndex = c[0].getColumnIndex("idCat");

                        do {
                            Log.d("API",
                                    "ID = " + c[0].getInt(idColIndex) +
                                            ", name = " + c[0].getString(nameColIndex) +
                                            ", idCAt = " + c[0].getString(idCatColIndex));
                            STR += c[0].getString(idCatColIndex) + ", ";
                            Log.d("API", "STR = " + STR);
                        } while (c[0].moveToNext());
                        STR = STR.substring(0, STR.length() - 2);
                        CardContentFragment.setCatStr(STR);
//                        SignIn.initEvents(STR);
                        STR2 = STR;
                        events = new Events(CardContentFragment.LENGTH, STR);
                        Log.d("API", "STR2: " + STR2);
                        STR = "";
                        Log.d("API", "STR = " + STR);
                    } else
                        Log.d("API", "0 rows");
                    c[0].close();
                    holder.btn.setText("Удалить");
                    flag.set(false);
                    c[0].close();
                } else {
                    db.execSQL("delete " + "from myTable");
                    if (!c[0].moveToNext())
                        Toast.makeText(getContext(), "delete", Toast.LENGTH_LONG).show();
                    holder.btn.setText("Добавить");
                    CardContentFragment.setCatStr("");

                    flag.set(true);
                }
                c[0].close();
                Log.d("API", "STR2: " + STR2);
                Events events = new Events(50, STR2);
                names = events.getNames();
                dates = events.getDates();
                categories = events.getCategories();
                links = events.getLinks();
            };
            holder.btn.setOnClickListener(onClickListener);

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int clearCount = db.delete("myTable", null, null);
        Log.d("API", "deleted rows count = " + clearCount);
    }

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
                    List<EventCategoryIncludeApiResponse> categoriesList;
                    if (eventsCategoriesApiResponse == null) Log.d("API", "response is null");
                    else {
                        categoriesList = eventsCategoriesApiResponse.getValues();
                        Log.d("API", "success");
                        Log.d("API", "categoryID: " + categoriesList.get(0).getId());
                        Log.d("API", "categoryList size: " + eventsCategoriesApiResponse.getValues().size());

                        for (int i = 0; i < categoriesList.size(); i++) {
                            categoryName.add(categoriesList.get(i).getName());
                            categoryId.add(categoriesList.get(i).getId());
                            Log.d("API", "catNAme: " + categoryName.get(i));
                        }

                        Sorts.this.ca.setNamesAndIds(categoryName, categoryId);

                    }
                } catch (Exception e) {
                    e.printStackTrace();}


            }

            @Override
            public void onFailure (@NonNull Call <EventsCategoriesApiResponse> call, @NonNull Throwable t){
                Log.d("API", "failed");
            }
        });
    }

}

