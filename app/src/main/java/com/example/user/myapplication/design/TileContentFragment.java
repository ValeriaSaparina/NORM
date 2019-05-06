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

import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.example.user.myapplication.design.CardContentFragment.i;

public class TileContentFragment extends Fragment {

    static Button btnDel;
    static int n = i;

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
            super(inflater.inflate(R.layout.item_tile, parent, false));
            picture = itemView.findViewById(R.id.tile_image);
            name = itemView.findViewById(R.id.tile_title);
            date = itemView.findViewById(R.id.tile_date_text);
            category = itemView.findViewById(R.id.tile_category_text);
            btnDel = itemView.findViewById(R.id.tile_delete_button);
        }

    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 7;
        private String[] names;
        private String[] categories;
        private String[] dates;

        String uID;

        private FirebaseDatabase database;
        private DatabaseReference myRef;
        private FirebaseAuth mAuth;

        ContentAdapter() {

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference();
            mAuth = FirebaseAuth.getInstance();

            uID = mAuth.getUid();

            names = new String[LENGTH];
            categories = new String[LENGTH];
            dates = new String[LENGTH];

            for (int i = 0; i < LENGTH; i++) {
                int finalI = i;
                Log.d("API", "i: " + i);
                myRef.child("events").child(Objects.requireNonNull(uID)).child("event" + i).child("name").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                names[finalI] = dataSnapshot.getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("DATABASE", "FAILED");
                            }
                        });

                myRef.child("events").child(uID).child("event" + i).child("date").addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dates[finalI] = dataSnapshot.getValue(String.class);
                                Log.d("DATABASE", "date: " + dates[finalI]);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("DATABASE", "FAILED");
                            }
                        });

                myRef.child("events").child(uID).child("event" + i).child("category").addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                categories[finalI] = dataSnapshot.getValue(String.class);
                                Log.d("DATABASE", "category: " + categories[finalI]);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d("DATABASE", "FAILED");
                            }
                        });
            }

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.name.setText(names[position % names.length]);
            holder.date.setText(dates[position]);
            holder.category.setText(categories[position]);
                Log.d("API", "nameI: " +  names[i] + " dateI: " + dates[i] + " categoryI: " + categories[i]);


            Log.d("API", "nameTile: " + names[position % names.length]);


            Picasso.get().load("https://pp.userapi.com/c855628/v855628072/369ef/Tan2nppGozE.jpg").into(holder.picture);

            View.OnClickListener onClickListener = v -> {
                if (v.getId() == R.id.tile_delete_button) {
                    myRef.child("events").child(uID).child("event" + position).setValue(null);
                    myRef.child("events").child(Objects.requireNonNull(uID)).child("event" + position).child("name").addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    names[position] = dataSnapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("DATABASE", "FAILED");
                                }
                            });
                    myRef.child("events").child(Objects.requireNonNull(uID)).child("event" + position).child("date").addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dates[position] = dataSnapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("DATABASE", "FAILED");
                                }
                            });
                    myRef.child("events").child(Objects.requireNonNull(uID)).child("event" + position).child("category").addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    categories[position] = dataSnapshot.getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.d("DATABASE", "FAILED");
                                }
                            });
                    holder.name.setText(names[position % names.length]);
                    holder.date.setText(dates[position]);
                    holder.category.setText(categories[position]);
                }
            };
            btnDel.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }


}

