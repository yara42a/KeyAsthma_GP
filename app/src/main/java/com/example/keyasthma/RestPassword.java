package com.example.keyasthma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class RestPassword extends AppCompatActivity {
    EditText emailtxt;
    Button resetbtn;
    ProgressBar progressBar;
    ImageButton Back;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth auth =FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);

        emailtxt = findViewById(R.id.EmailAddressReset);
        resetbtn = findViewById(R.id.Resetbtn);
        progressBar = findViewById(R.id.progressBar2);
        Back=findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestPassword.this,LoginPage.class));
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    public void resetPassword(){
        String email = emailtxt.getText().toString().trim();

        if(email.isEmpty())
        {
            emailtxt.setError("Email is required!");
            emailtxt.requestFocus();
            return;
        }
        if(!email.matches(emailPattern)){
            emailtxt.setError("Please provide valid email!");
            emailtxt.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RestPassword.this, "Check your email to reset your password!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RestPassword.this,LoginPage.class));
                }else{
                    Toast.makeText(RestPassword.this,"This email is not registered! ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}