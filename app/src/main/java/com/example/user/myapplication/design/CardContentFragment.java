package com.example.user.myapplication.design;

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

import com.example.user.myapplication.API.API;
import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.API.EventsResponse;
import com.example.user.myapplication.R;
import com.google.firebase.FirebaseApp;
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
    private static List<EventResponse> myList;
    static Button btn;
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
        public TextView name;
        TextView date;
        TextView category;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            date = itemView.findViewById(R.id.card_date_text);
            category = itemView.findViewById(R.id.card_category_text);
            btn = itemView.findViewById(R.id.action_button);
        }

    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 18;
        private String[] names;
        private String[] categories;
        private String[] dates;

        ContentAdapter() {

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            Users users = new Users();
            users.read(mAuth.getUid());

            Log.d("API", "nameWrite: " + users.getNameUser());

            names = new String[LENGTH];
            categories = new String[LENGTH];
            dates = new String[LENGTH];
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
            Call<EventsResponse> call = api.eventList(LENGTH, 70, cityList);

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

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Log.d("API", "name2: " + names[position % names.length]);
            holder.name.setText(names[position % names.length]);
            Picasso.get().load("https://pp.userapi.com/c855628/v855628072/369ef/Tan2nppGozE.jpg").into(holder.picture);
            holder.date.setText(dates[position]);
            holder.category.setText(categories[position]);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("events");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();

            View.OnClickListener onClickListener = v -> {
                if (v.getId() == R.id.action_button) {
                    myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name").setValue(holder.name.getText());
                    myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("date").setValue(holder.date.getText());
                    myRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("category").setValue(holder.category.getText());
                    i++;
                }
            };
            btn.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}

