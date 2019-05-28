package com.example.user.myapplication.pages;

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
import com.example.user.myapplication.design.Events;
import com.example.user.myapplication.design.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Button btnVhod2;
    EditText etEmail;
    EditText etPassword;

    public static Events events;
    private FirebaseAuth mAuth;

    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnVhod2 = findViewById(R.id.vhod2);
        btnVhod2.setOnClickListener(this);

        etEmail = findViewById(R.id.E_mailVhod);
        etPassword = findViewById(R.id.passwordVhod);

        mAuth = FirebaseAuth.getInstance();
        users = new Users();

        events = new Events(50, "");

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d("API", "mAuth.getCurrentUser().getUid(): " + mAuth.getCurrentUser().getUid());
            Log.d("API", "Objects.requireNonNull(mAuth.getCurrentUser()).getUid(): " + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
            Users.setUID(mAuth.getCurrentUser().getUid());
            users.read();
            Intent intent = new Intent(SignIn.this, Content.class);
            startActivity(intent);
        }

    }

    private void signIn (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("authentication", "signInWithEmail:success");

                        Log.d("API", "Objects.requireNonNull(mAuth.getCurrentUser()).getUid(): " + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                        Users.setUID(mAuth.getCurrentUser().getUid());
                        users.read();

                        CLICK();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("authentication", "signInWithEmail:failure", task.getException());
                        ERROR();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vhod2) {
            signIn(etEmail.getText().toString(), etPassword.getText().toString());

        } else
        if (v.getId() == R.id.reg) {
            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
        }
    }

    private void CLICK() {
        Intent intent = new Intent(this, Content.class);
        startActivity(intent);

    }

    private void ERROR() {
        Toast.makeText(SignIn.this, "Проверьте правильность введенных данных", Toast.LENGTH_SHORT).show();
    }
}
