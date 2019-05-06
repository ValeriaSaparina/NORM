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
import com.example.user.myapplication.design.Des;
import com.example.user.myapplication.design.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Button btnVhod2;
    EditText etEmail;
    EditText etPassword;

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

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
        users.read(mAuth.getUid());
            Intent intent = new Intent(SignIn.this, Des.class);
            startActivity(intent);
        }

    }

    private void signIn (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("authentication", "signInWithEmail:success");

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
            users.read(mAuth.getUid());
        } else
        if (v.getId() == R.id.reg) {
            Intent intent = new Intent(this, Registration.class);
            startActivity(intent);
        }
    }

    private void CLICK() {
        Intent intent = new Intent(this, Des.class);
        startActivity(intent);

    }

    private void ERROR() {
        Toast.makeText(SignIn.this, "Проверьте правильность введенных данных", Toast.LENGTH_SHORT).show();
    }
}
