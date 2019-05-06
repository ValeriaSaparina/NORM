package com.example.user.myapplication.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnVhodMain;
    Button btnZaregMain;

    public String username;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth.signOut();

        btnVhodMain = findViewById(R.id.vhod1);
        btnVhodMain.setOnClickListener(this);


        btnZaregMain = findViewById(R.id.zareg1);
        btnZaregMain.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, Main.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vhod1:
                Intent intent = new Intent(this, SignIn.class);
                startActivity(intent);
                break;

            case R.id.zareg1:
                Intent intent2 = new Intent(this, Registration.class);
                startActivity(intent2);
                break;
        }
    }
}
