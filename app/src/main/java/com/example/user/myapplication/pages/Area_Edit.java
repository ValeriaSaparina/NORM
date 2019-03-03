package com.example.user.myapplication.pages;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.user.myapplication.R;
import com.example.user.myapplication.UserInformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Area_Edit extends AppCompatActivity  {

    private ListView ListUserTasks;
    private String str;

    private String Userid;

    private ArrayList DiscrTasks;

    private Toolbar tOOlbAr;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area__edit);


        tOOlbAr = findViewById(R.id.toolbar);
        setSupportActionBar(tOOlbAr);

        ListUserTasks = findViewById(R.id.discr_for_tasks);

        myRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Userid = user.getUid();

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                //str = dataSnapshot.child(user.getUid()).child("information").getValue(String.class);

                showData(dataSnapshot);
                updateUI();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("HELP", "(((");
            }
        });

    }

    public void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInformation UserInfo = new UserInformation();
            UserInfo.setName(ds.child(Userid).child("information").getValue(UserInformation.class).getName());
            UserInfo.setSurname(ds.child(Userid).getValue(UserInformation.class).getSurname());
            UserInfo.setEmail(ds.child(Userid).getValue(UserInformation.class).getEmail());
            UserInfo.setInformation(ds.child(Userid).getValue(UserInformation.class).getInformation());

            Log.d("DATABASE", UserInfo.getEmail());
            Log.d("DATABASE", UserInfo.getInformation());
            Log.d("DATABASE", UserInfo.getSurname());
            Log.d("DATABASE", UserInfo.getName());

            ArrayList<String> array = new ArrayList<>();
            array.add(UserInfo.getName());
            array.add(UserInfo.getSurname());
            array.add(UserInfo.getInformation());
            array.add(UserInfo.getEmail());

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
            ListUserTasks.setAdapter(adapter);
        }
    }

    private void updateUI() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1);

        ListUserTasks.setAdapter(adapter);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.Save:
//
//                Name = EditName.getText().toString();
//                age = EditAge.getText().toString();
//                information = EditInformation.getText().toString();
//
//                PAName.setText(Name);
////                PAAge.setText(age, TextView.BufferType.EDITABLE);
////                PAInformation.setText(information, TextView.BufferType.EDITABLE);
//
//                Intent intent = new Intent(this, PersonalArea.class);
//                startActivity(intent);
//                break;
//        }
//    }

}
