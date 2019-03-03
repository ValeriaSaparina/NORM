package com.example.user.myapplication.pages;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myapplication.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity implements View.OnClickListener {


    Button btnZareg2;
    EditText etNameZareg;
    EditText etSurnameZareg;
    EditText etMailZareg;
    EditText etPassword1Zareg;
    EditText etPassword2Zareg;


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
        etPassword1Zareg = findViewById(R.id.password1Zareg);
        etPassword2Zareg = findViewById(R.id.password2Zareg);

        mAuth = FirebaseAuth.getInstance();




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void createAccount(String email, String password) {
        Log.d("jj", "createAccount:" + email);




        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("jj", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            CLICK();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("jj", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.zareg2) {
            createAccount(etMailZareg.getText().toString(), etPassword1Zareg.getText().toString());

            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }


    }

    private void CLICK() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
