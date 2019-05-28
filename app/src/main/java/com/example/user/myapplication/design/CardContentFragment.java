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

import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.R;
import com.example.user.myapplication.pages.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardContentFragment extends Fragment {

    static final String BASE_URL = "https://api.timepad.ru/";
    private static final int LENGTH = 50;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static List<EventResponse> myList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    static int i;

    static String categoriesStr = "";

    protected static void setCatStr(String str) {
        categoriesStr = str;
    }

    private static String[] names;
    private static String[] categories;
    private static String[] dates;
    private static String[] links;
    private Events eventsCat;


    RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        qqq();
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
//            qqq();
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            date = itemView.findViewById(R.id.card_date_text);
            category = itemView.findViewById(R.id.card_category_text);
            btn_add = itemView.findViewById(R.id.action_button);
            btn_link = itemView.findViewById(R.id.site_button);

        }

    }

    String nameF;
    String[] namesF = new String[10];
    final long[] count = new long[1];

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("events");
    DatabaseReference usersRef = rootRef.child(Objects.requireNonNull(mAuth.getUid()));
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        ContentAdapter() {

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

                names = new String[LENGTH];
                categories = new String[LENGTH];
                dates = new String[LENGTH];
                links = new String[LENGTH];



                if(categoriesStr.equals("")) {
                    Events events = SignIn.events;
                    names = events.getNames();
                    dates = events.getDates();
                    categories = events.getCategories();
                    links = events.getLinks();
                    notifyDataSetChanged();
                    notifyItemInserted(0);
                    Log.d("API", "namesCard: " + names[0]);
                } else {
                    eventsCat = new Events(LENGTH, categoriesStr);
                    names = eventsCat.getNames();
                    dates = eventsCat.getDates();
                    categories = eventsCat.getCategories();
                    links = eventsCat.getLinks();
                    notifyDataSetChanged();
                    notifyItemInserted(0);
                    Log.d("API", "namesCard: " + names[0]);
                }


//                Log.d("API", "UID events: " + mAuth.getUid());
//            for (int i = 1; i < 2; i++) {
//                int finalI = i;
//                myRef.child("events").child(mAuth.getUid()).child("event" + i).child("name").addListenerForSingleValueEvent(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                namesF[finalI] = Objects.requireNonNull(dataSnapshot.getValue(String.class));
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
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
            holder.date.setText("Дата: " + dates[position ]);
            holder.category.setText("Категория: " + categories[position]);



//            ValueEventListener valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    count[0] = dataSnapshot.getChildrenCount();
//                    Log.d("TAG", "count= " + count[0]);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {}
//            };
//            usersRef.addListenerForSingleValueEvent(valueEventListener);
//
//            try {
//                for (int i = 0; i < 10; i++) {
//                    if (namesF[i] == holder.name.getText()) {
//                        holder.btn_add.setImageResource(R.drawable.ic_favorite);
//                        Log.d("API", "equals: " + names[position] + " + "
//                                + rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name"));
//                    }
//                    Log.d("API", "equals: " + names[position] + " + "
//                            + rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name"));
//                    }
//                } catch (NullPointerException ex) {
//                ex.getMessage();
//            }


                    View.OnClickListener onClickListenerAdd = v -> {
                if (v.getId() == R.id.action_button) {
                    if (i == 9) {
                        Toast.makeText(getContext(), "Список выбранных событий полон", Toast.LENGTH_LONG).show();
                        i = 0;
                    } else {
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("name").setValue(names[position]);
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("date").setValue(dates[position]);
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("category").setValue(categories[position]);
                        rootRef.child(Objects.requireNonNull(mAuth.getUid())).child("event" + i).child("link").setValue(links[position]);
                        holder.btn_add.setImageResource(R.drawable.ic_favorite);
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


//            holder.btn_add.setImageResource();
        }


        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}

