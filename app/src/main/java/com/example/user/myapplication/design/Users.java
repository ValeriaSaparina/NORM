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
    private static String surnameUser;
    private static String mailUser;
    private static String cityUser;
    private static String passwordUser;
    private static String uID;

    public Users() {

    }

    public void read() {
        final String[] str = {""};
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uID).child("name").addListenerForSingleValueEvent(
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

        mDatabase.child("users").child(uID).child("surname").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "surname: " + str[0]);
                        surnameUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });


        mDatabase.child("users").child(uID).child("e-mail").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "e-mail: " + str[0]);
                        mailUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });

        mDatabase.child("users").child(uID).child("password").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "name: " + str[0]);
                        passwordUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });

        mDatabase.child("users").child(uID).child("city").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        str[0] = dataSnapshot.getValue(String.class);
                        Log.d("DATABASE", "city: " + str[0]);
                        cityUser = str[0];
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("DATABASE", "FAILED");
                    }
                });
    }

    public void write() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").child(uID).child("name").setValue(nameUser);
        myRef.child("users").child(uID).child("surname").setValue(surnameUser);
        myRef.child("users").child(uID).child("e-mail").setValue(mailUser);
        myRef.child("users").child(uID).child("password").setValue(passwordUser);
        myRef.child("users").child(uID).child("city").setValue(cityUser);
    }





    public String getmailUser() {
        return mailUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public String getCityUser() {
        return cityUser;
    }

    public static String getUID() {
        return uID;
    }

    public static void setUID(String uID) {
        Users.uID = uID;
    }

    public static void setNameUser(String nameUser) {
        Users.nameUser = nameUser;
    }

    public static void setSurnameUser(String surnameUser) {
        Users.surnameUser = surnameUser;
    }

    public static void setMailUser(String mailUser) {
        Users.mailUser = mailUser;
    }

    public static void setCityUser(String cityUser) {
        Users.cityUser = cityUser;
    }

    public static void setPasswordUser(String passwordUser) {
        Users.passwordUser = passwordUser;
    }

}

