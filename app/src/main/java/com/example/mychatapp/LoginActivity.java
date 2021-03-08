package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    TextView txtSignUp;
    EditText loginEmail , loginPass;
    Button signInBtn;

    private ProgressBar mprogressBarOfMainActivity;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = findViewById(R.id.txtSignUp);
        signInBtn = findViewById(R.id.signInBtnn);
        loginEmail = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);

        firebaseAuth = FirebaseAuth.getInstance();

        mprogressBarOfMainActivity = findViewById(R.id.progressBarOfMainActivity);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            finish();
            startActivity(new Intent(LoginActivity.this , HomeActivity.class));
        }

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , SignUp.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = loginEmail.getText().toString().trim();
                String pass = loginPass.getText().toString().trim();

                if (mail.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }else{
                    //log in the user
                    mprogressBarOfMainActivity.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail , pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){
                                checkMailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Account Does Not Exist !!", Toast.LENGTH_SHORT).show();
                                mprogressBarOfMainActivity.setVisibility(View.INVISIBLE);

                        }
                        }
                    });


                }
            }

        });

    }
    private void checkMailVerification() {

        //checkMailVerification
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified() == true){
            Toast.makeText(getApplicationContext(), "Logged In" , Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this , HomeActivity.class));
        }else{

            mprogressBarOfMainActivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Verify Your Mail First", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
