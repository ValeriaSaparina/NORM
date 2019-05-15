package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.myapplication.R;

public class Edit extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEdit;
    private EditText cityEdit;
    private EditText mailEdit;
    private Button btn_save;

    private Users users;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        users = new Users();

        nameEdit = findViewById(R.id.title_edit);
        cityEdit = findViewById(R.id.city_text_edit);
        mailEdit = findViewById(R.id.mail_text_edit);

        btn_save = findViewById(R.id.save_button_edit);
        btn_save.setOnClickListener(this);

        nameEdit.setText(users.getNameUser() + " " + users.getSurnameUser());
        mailEdit.setText(users.getmailUser());
        cityEdit.setText(users.getCityUser());
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button_edit) {

            String[] str;
            str = nameEdit.getText().toString().split(" ");

            Users.setMailUser(mailEdit.getText().toString());
            Users.setCityUser(cityEdit.getText().toString());
            Users.setNameUser(str[0]);
            Users.setSurnameUser(str[1]);
            users.write();

            Intent intent = new Intent(this, Des.class);
            startActivity(intent);
        }
    }
}
