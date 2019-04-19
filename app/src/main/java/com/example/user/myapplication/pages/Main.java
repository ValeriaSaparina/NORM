package com.example.user.myapplication.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.R;

public class Main extends AppCompatActivity implements View.OnClickListener {

    Button btnEvents;
    Button btnNews;
    Button btnTours;
    Button btnGuides;
    Button btnFindFriends;
    Button btnPersonalArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnEvents = findViewById(R.id.Events);
        btnEvents.setOnClickListener(this);

//        btnNews = findViewById(R.id.News);
//        btnNews.setOnClickListener(this);
//
//        btnTours = findViewById(R.id.Tours);
//        btnTours.setOnClickListener(this);

        btnGuides = findViewById(R.id.Guides);
        btnGuides.setOnClickListener(this);

        btnFindFriends = findViewById(R.id.FindFriends);
        btnFindFriends.setOnClickListener(this);

        btnPersonalArea = findViewById(R.id.PersonalArea);
        btnPersonalArea.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.Events:
                intent = new Intent(this, Events.class);
                startActivity(intent);
                break;

//            case R.id.News:
//                intent = new Intent(this, News.class);
//                startActivity(intent);
//                break;
//
//            case R.id.Tours:
//                intent = new Intent(this, Tours.class);
//                startActivity(intent);
//                break;

            case R.id.Guides:
                intent = new Intent(this, Guides.class);
                startActivity(intent);
                break;

            case R.id.FindFriends:
                intent = new Intent(this, FindFriends.class);
                startActivity(intent);
                break;

            case R.id.PersonalArea:
                intent = new Intent(this, PersonalArea.class);
                startActivity(intent);
                break;
        }
    }
} //TODO:Переход назад
