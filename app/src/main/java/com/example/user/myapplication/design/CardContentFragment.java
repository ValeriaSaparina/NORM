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
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static List<EventResponse> myList;
    @SuppressLint("StaticFieldLeak")
    static int i;

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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView name;
        TextView date;
        TextView category;
        Button btn_add;
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
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 50;
        private String[] names;
        private String[] categories;
        private String[] dates;
        private String[] links;

        ContentAdapter() {
            Users users = new Users();
                if (mAuth.getCurrentUser() != null) {
                    Log.d("API", "User is not null");
                    Log.d("API", "uID: " + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                    Users.setUID(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                    Log.d("API", "===: " + users.getNameUser());
                } else {
                    Log.d("API", "User is null");
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
            Call<EventsResponse> call = api.eventList(LENGTH, cityList);

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
                                Log.d("API", "str: " + dates[i]);
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

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("API", "name2: " + names[position % names.length]);
            holder.name.setText(names[position % names.length]);
            Picasso.get().load("https://pp.userapi.com/c845524/v845524621/20d268/x3tFRfkMiCs.jpg").into(holder.picture);
            holder.date.setText("Дата: " + dates[position]);
            holder.category.setText("Категория: " + categories[position]);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("events");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            View.OnClickListener onClickListenerAdd = v -> {
                if (v.getId() == R.id.action_button) {
                    if (i == LENGTH - 1) {
                        Toast.makeText(getContext(), "Список выбранных событий полон", Toast.LENGTH_LONG).show();
                        i = 0;
                    } else {
                        myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name").setValue(names[position]);
                        myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("date").setValue(dates[position]);
                        myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("category").setValue(categories[position]);
                        myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("link").setValue(links[position]);
                        i++;
                    }
                }

            };
            holder.btn_add.setOnClickListener(onClickListenerAdd);

            View.OnClickListener onClickListener = v -> {
                if (v.getId() == R.id.site_button) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(links[position]));
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


}

