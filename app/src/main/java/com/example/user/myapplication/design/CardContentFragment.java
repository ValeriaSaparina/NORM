package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.API.EventsResponse;
import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardContentFragment extends Fragment {

    static final String BASE_URL = "https://api.timepad.ru/";
    protected static final int LENGTH = 50;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static List<EventResponse> myList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    static int i;
    ContentAdapter ca;

    static String categoriesStr = "";

    protected static void setCatStr(String str) {
        categoriesStr = str;
    }


    RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        ca = adapter;
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView name;
        TextView date;
        TextView category;
        ImageButton btn_add;
        Button btn_link;


        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            date = itemView.findViewById(R.id.card_date_text);
            category = itemView.findViewById(R.id.card_category_text);
            btn_add = itemView.findViewById(R.id.action_button);
            btn_link = itemView.findViewById(R.id.site_button);

        }

    }


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("events");
    DatabaseReference usersRef = rootRef.child(Objects.requireNonNull(mAuth.getUid()));
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {


        List<String> myNames;
        List<String> myCategories;
        List<String> myDates;
        List<String> myLinks;

        void setInfo(List<String> names, List<String> categories, List<String> dates, List<String> links) {
            this.myNames = names;
            this.myCategories = categories;
            this.myDates = dates;
            this.myLinks = links;
            notifyDataSetChanged();

            Log.d("API", "setInfo name: " + myNames.get(0));
        }
        ContentAdapter() {

            myNames = new ArrayList<>();
            myCategories = new ArrayList<>();
            myDates = new ArrayList<>();
            myLinks = new ArrayList<>();

//            myNames.add("");
//            myCategories.add("");
//            myDates.add("");
//            myLinks.add("");

            Users users = new Users();
            if (mAuth.getCurrentUser() != null) {
                Users.setUID(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            } else {
                Users.setUID(null);
            }

            try {
                if (users.getNameUser() == null) {
                    Users.setUID(mAuth.getCurrentUser().getUid());
                    users.read();
                } else {
                    Users.setUID(mAuth.getCurrentUser().getUid());
                    Log.d("API", "name: " + users.getNameUser() + " " + users.getSurnameUser()
                            + "; city: " + users.getCityUser() + "; Uid: " + Users.getUID());
                    users.write();
                }
            } catch (NullPointerException ex) {
                Log.d("API", "exception: " + null);
            }

            Log.d("API", "nameWrite: " + users.getNameUser());

        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Log.d("API", "bind position: " + position);
//            Log.d("API", "categories size: "+ (myNames.size() == 0 ? "null" : String.valueOf(.size())));


            if(myNames.size() == 0) Log.d("API", "NAME BIND: null");
            else {
                int listSize = myNames.size();
                if (listSize > position) Log.d("API", (" NAME BIND" + String.valueOf(listSize == 1 ? myNames.get(listSize - 1) : myNames.get(position))));
            }

            if(myNames.size() == 0) holder.name.setText("");
            else {
                int listSize = myNames.size();
                if (listSize > position) holder.name.setText(listSize == 1 ? myNames.get(listSize - 1) : myNames.get(position));
            }
            if(myCategories.size() == 0) holder.category.setText("");
            else {
                int listSize = myCategories.size();
                if (listSize > position) holder.category.setText(listSize == 1 ? myCategories.get(listSize - 1) : myCategories.get(position));
            }
            if(myDates.size() == 0) holder.date.setText("");
            else {
                int listSize = myDates.size();
                if (listSize > position) holder.date.setText(listSize == 1 ? myDates.get(listSize - 1) : myDates.get(position));
            }

            Picasso.get().load("https://pp.userapi.com/c845524/v845524621/20d268/x3tFRfkMiCs.jpg").into(holder.picture);


//            Log.d("API", "nameEvent: " + myNames.get());
//            holder.name.setText(names[position % names.length]);
//
//            holder.date.setText("Дата: " + dates[position ]);
//            holder.category.setText("Категория: " + categories[position]);


                    View.OnClickListener onClickListenerAdd = v -> {
                if (v.getId() == R.id.action_button) {
                    if (i == 9) {
                        Toast.makeText(getContext(), "Список выбранных событий полон", Toast.LENGTH_LONG).show();
                        i = 0;
                    } else {
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name").setValue(myNames.get(position));
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("date").setValue(myDates.get(position));
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("category").setValue(myCategories.get(position));
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("link").setValue(myLinks.get(position));
                        holder.btn_add.setImageResource(R.drawable.ic_favorite);
                        i++;
                    }
                }

            };
            holder.btn_add.setOnClickListener(onClickListenerAdd);

            View.OnClickListener onClickListener = v -> {
                if (v.getId() == R.id.site_button) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myLinks.get(position)));
                    startActivity(browserIntent);
                }
            };
            holder.btn_link.setOnClickListener(onClickListener);
        }


        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

      public void onStart() {
        super.onStart();

        List<String> names = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> links = new ArrayList<>();

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

        Log.d("API", " start categoryStr: " + categoriesStr);
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
                            names.add(myList.get(i).getName());
                            categories.add(myList.get(i).getCategories().get(0).getName());
                            links.add(myList.get(i).getUrl());
                            myList.get(i).getStarts_at().getChars(0, 10, dst, 0);
                            for(char c : dst) str[0] += c;
                            dates.add(str[0]);
                            Log.d("API", "STR: " + dates.get(0));
                        }

                        CardContentFragment.this.ca.setInfo(names, categories, dates, links);
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



    @Override
    public void onResume() {
        super.onResume();
        if (!categoriesStr.equals("")) {
            List<String> names = new ArrayList<>();
            List<String> categories = new ArrayList<>();
            List<String> dates = new ArrayList<>();
            List<String> links = new ArrayList<>();

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

            Log.d("API", "Resume categoryStr: " + categoriesStr);
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
                                char[] dst = new char[10];
                                names.add(myList.get(i).getName());
                                categories.add(myList.get(i).getCategories().get(0).getName());
                                links.add(myList.get(i).getUrl());
                                myList.get(i).getStarts_at().getChars(0, 10, dst, 0);
                                for (char c : dst) str[0] += c;
                                dates.add(str[0]);
                                CardContentFragment.this.ca.setInfo(names, categories, dates, links);
                                Log.d("API", "STR: " + dates.get(0));
                                Log.d("API", "***********************");
                            }
                            Log.d("API", "onResume setInfo: ok");

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
    }
}

