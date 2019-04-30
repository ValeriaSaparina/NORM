package com.example.user.myapplication.design;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Users {

    private static String nameUser;
    private static String lastnameUser;
    private static String aboutUser;

    public Users(String uID) {
        final String[] str = {""};
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).child("name").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "name: " + str[0]);
                        nameUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });

        mDatabase.child(uID).child("lastname").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "lastname: " + str[0]);
                        lastnameUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });

        mDatabase.child(uID).child("about").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "about: " + str[0]);
                        aboutUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });
    }



    public String getNameUser() {
        return nameUser;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public String getAboutUser() {
        return aboutUser;
    }
}

