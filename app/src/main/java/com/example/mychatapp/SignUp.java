package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUp extends AppCompatActivity {

    TextView txtSignIn;
    ImageView profileImage;
    EditText registrationEmail, registrationPass, registrationConfirmPass , regName;
    Button btnSignup;

    Uri imageUri;
    String imageURI;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait ... ");
        progressDialog.setCancelable(false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();




        txtSignIn = findViewById(R.id.txtSignIn);
        profileImage = findViewById(R.id.profileImage);
        registrationEmail = findViewById(R.id.registrationEmail);
        regName = findViewById(R.id.registrationName);
        registrationPass = findViewById(R.id.registrationPass);
        registrationConfirmPass = findViewById(R.id.registrationConfirmPass);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                final String name = regName.getText().toString();
                final String pass = registrationPass.getText().toString();
                final String mail = registrationEmail.getText().toString().trim();
                String cPass = registrationConfirmPass.getText().toString();
                final String status = "Hey There I'm Using Chat App";

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) ||
                        TextUtils.isEmpty(pass) || TextUtils.isEmpty(cPass)){
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(cPass)){
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Password Does Not Match !", Toast.LENGTH_SHORT).show();
                }
                else if (pass.length() < 6){
                    progressDialog.dismiss();
                    registrationPass.setError("Password Should be Greater Than 6 Character!");
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(mail , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this, "user created successfully !", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();

                                final DatabaseReference databaseReference = firebaseDatabase.getReference().child("user").child(firebaseAuth.getUid());
                                final StorageReference storageReference = firebaseStorage.getReference().child("upload").child(firebaseAuth.getUid());

                                if (imageUri != null){
                                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageURI = uri.toString();
                                                        Users user = new Users(firebaseAuth.getUid() , name , mail  , imageURI , status);
                                                        //data is in users u have to put it in refrence
                                                        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    progressDialog.dismiss();
                                                                    startActivity(new Intent(SignUp.this , HomeActivity.class));
                                                                }
                                                                else{
                                                                    Toast.makeText(SignUp.this, "error in creating new user", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else{
                                    String status = "Hey There I'm Using Chat App";
                                    //default dp
                                    imageURI = "https://firebasestorage.googleapis.com/v0/b/notesapp-d60e9.appspot.com/o/user.png?alt=media&token=8ba42627-d5d5-4e40-ab05-c43dddaf5352";
                                    Users user = new Users(firebaseAuth.getUid() , name , mail  , imageURI , status);
                                    //data is in users u have to put it in refrence
                                    databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                startActivity(new Intent(SignUp.this , HomeActivity.class));
                                            }
                                            else{
                                                Toast.makeText(SignUp.this, "error in creating new user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(SignUp.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }


                        }

                    });
                }


            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  stackoverflow

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10){
            if (data != null){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }

    // send email verification
    private void sendEmailVerification() {

        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(SignUp.this, "Verifivation Email is Sent, Verify and Login Again", Toast.LENGTH_SHORT).show();
                    sendEmailVerification();
                    firebaseAuth.signOut();
                    startActivity(new Intent(SignUp.this , MainActivity.class));
                }
            });
        }
        else{
            Toast.makeText(SignUp.this, "Failed to send verification Email !!", Toast.LENGTH_SHORT).show();
        }
    }
}
