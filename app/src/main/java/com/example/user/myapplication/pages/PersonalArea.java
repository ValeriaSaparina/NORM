package com.example.user.myapplication.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.R;

public class PersonalArea extends AppCompatActivity implements View.OnClickListener{

    Button Edit;
    Button GuidAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        Edit = findViewById(R.id.editButton);
        Edit.setOnClickListener(this);

        GuidAdd = findViewById(R.id.guideAdd);
        GuidAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editButton:
                Intent intent = new Intent(this, Area_Edit.class);
                startActivity(intent);
                break;

//            case R.id.zareg1:
//                Intent intent2 = new Intent(this, .class);
//                startActivity(intent2);
//                break;
            //TODO: сделай обработку добавления в гиды пользователей
        }
        //TODO: сделаей вместо EditTExt TextView
    }


}
