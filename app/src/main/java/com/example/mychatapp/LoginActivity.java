package com.example.mychatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    TextView txtSignUp;
    EditText loginEmail , loginPass;
    Button signInBtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = findViewById(R.id.txtSignUp);
        signInBtn = findViewById(R.id.signInBtnn);
        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , SignUp.class));
            }
        });

    }
}
