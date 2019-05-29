package com.example.user.myapplication.pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapplication.R;
import com.example.user.myapplication.design.Content;
import com.example.user.myapplication.design.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class Registration extends AppCompatActivity implements View.OnClickListener {


    Button btnZareg2;
    EditText etNameZareg;
    EditText etSurnameZareg;
    EditText etMailZareg;
    EditText etPassword1Zareg;
    EditText etCityReg;

    public String mail;
    public String name;
    public String city;
    public String surname;
    public String password;

    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnZareg2 = findViewById(R.id.zareg2);
        btnZareg2.setOnClickListener(this);

        etNameZareg = findViewById(R.id.nameZareg);
        etSurnameZareg = findViewById(R.id.surnameZareg);
        etMailZareg = findViewById(R.id.e_mailZareg);
        etPassword1Zareg = findViewById(R.id.passwordZareg);
        etCityReg = findViewById(R.id.cityReg);

        mAuth = FirebaseAuth.getInstance();




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        currentUser = mAuth.getCurrentUser();
    }

    private void createAccount(String email, String password) {
        Log.d("jj", "createAccount:" + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                       // Users users = new Users();
                        Log.d("jj", "createUserWithEmail:success");
                        Log.d("API", "uID REG: " + mAuth.getCurrentUser().getUid());
                       // users.write(mAuth.getUid());

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("jj", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Registration.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View v) {

        password = etPassword1Zareg.getText().toString();
        mail = etMailZareg.getText().toString();
        surname = etSurnameZareg.getText().toString();
        name = etNameZareg.getText().toString();
        city = etCityReg.getText().toString();


        if (v.getId() == R.id.zareg2) {
            if (password.length() >= 6) {
                createAccount(etMailZareg.getText().toString(), etPassword1Zareg.getText().toString());

                Users users = new Users();

                Users.setNameUser(name);
                Users.setSurnameUser(surname);
                Users.setMailUser(mail);
                Users.setPasswordUser(password);
                Users.setCityUser(city);
                Log.d("API", "SUCCESS REG");
 //               Users.setUID(mAuth.getCurrentUser().getUid());


//                users.write(mAuth.getUid());
                click();

                Intent intent = new Intent(this, Content.class);
                startActivity(intent);
            }
            else Toast.makeText(this, "у тебя точно больше 6 символов в пароле?)", Toast.LENGTH_LONG);
            //Log.d("API", "Uid if:" + mAuth.getUid());
            try {
                Log.d("API", "mAuth.getCurrentUser().getUid(): " + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            } catch (NullPointerException ex) {
                Log.d("API", "exception: " + ex.getMessage());
            }
        }


    }

    private void click() {
        Intent intent = new Intent(this, Content.class);
        startActivity(intent);
    }
}
