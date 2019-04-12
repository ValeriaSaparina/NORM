package com.example.user.myapplication;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Users {

    private DatabaseReference mDatabase;

    private String name;
    public String lastname;
    private String about;

//    private String information;
//    private String email;

    public Users() {
        name = "name";
        lastname = "lastname";
        about = "aBout";
    }

    public String getName(String uID) {
        final String[] Name = {""};
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).child("about").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
  //                      Name[0] = user.name;
                        Log.d("qqq", user.toString() + user.about);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("qqq", "FAILED");                    }
                });
        return Name[0];
    }

    public String getLastname(String uID) {
        final String[] lastName = {""};
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        //lastName[0] = user.lastname;
                        Log.d("qqq", user.toString() );
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("qqq", "FAILED");                    }
                });
        return lastName[0];
    }

    public String getAbout(String uID) {
        final String[] About = {""};
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(uID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        About[0] = user.about;
                        Log.d("qqq", About[0]);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("qqq", "FAILED");
                    }
                });
        return About[0];
    }
}

