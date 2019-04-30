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
import com.example.user.myapplication.design.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    Button btnVhod2;
    EditText etEmail;
    EditText etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnVhod2 = findViewById(R.id.vhod2);
        btnVhod2.setOnClickListener(this);

        etEmail = findViewById(R.id.E_mailVhod);
        etPassword = findViewById(R.id.passwordVhod);

        mAuth = FirebaseAuth.getInstance();

    }

    private void signIn (String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("authentication", "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();

                            //проверка бфзы данных
//                            Users users = new Users();
//                            users.getName(user.getUid());
//                            users.getLastname(user.getUid());
//                            users.getAbout(user.getUid());

                            CLICK();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("authentication", "signInWithEmail:failure", task.getException());
                            ERROR();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vhod2) {
            signIn(etEmail.getText().toString(), etPassword.getText().toString());
        }
    }

    private void CLICK() {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);

    }

    private void ERROR() {
        Toast.makeText(SignIn.this, "Проверьте правильность введенных данных", Toast.LENGTH_SHORT).show();
    }
}
