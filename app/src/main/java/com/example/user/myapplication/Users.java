package com.example.user.myapplication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Users {

    private DatabaseReference mDatabase;

    public Users() {
    }

    public String getName(String uID) {
        final String[] str = {""};
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).child("name").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "name: " + str[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");                    }
                });
        return str[0];
    }

    public String getLastname(String uID) {
        final String[] str = new String[1];
                mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).child("lastname").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "lastname: " + str[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");                    }
                });
        return str[0];
    }

    public String getAbout(String uID) {
        final String[] str = {""};
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).child("about").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "about: " + str[0]);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });
        return str[0];
    }
}

