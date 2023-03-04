package com.example.keyasthma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://key-asthma-default-rtdb.firebaseio.com");
    EditText EmailAddressLogIn, PasswordLogIn;
    TextView registerNowBtn,restpass;
    Button Login_btn;
    ImageButton back;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseAuth mUser = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    //String userID = user.getUid();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        EmailAddressLogIn = findViewById(R.id.EmailAddressLogIn);
        PasswordLogIn = findViewById(R.id.PasswordLogIn);
        registerNowBtn = findViewById(R.id.registerNowBtn);
        Login_btn = findViewById(R.id.LogIn_btn);
        progressDialog=new ProgressDialog(this);
        restpass=findViewById(R.id.restpass);
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,Welcome.class));
            }
        });


        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this,SignUp.class));
            }
        });
        restpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, RestPassword.class));
            }
        });
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();
            }
        });

    }

    private void perforLogin() {
        String EmailAddTxt = EmailAddressLogIn.getText().toString();
        String  PassTxt = PasswordLogIn.getText().toString();

        if(!EmailAddTxt.matches(emailPattern)){
            EmailAddressLogIn.setError("Enter Connext Email");}
        else if(PassTxt.isEmpty() || PassTxt.length()<6){
            PasswordLogIn.setError("Enter Proper Password");
        }else{
            progressDialog.setMessage("Please wait while Login....");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(EmailAddTxt,PassTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginPage.this,"Login successfully",Toast.LENGTH_SHORT);

                        FirebaseUser user = mAuth.getCurrentUser();
                        String userID = user.getUid();
                        String Newpass = PassTxt;
                        FirebaseDatabase.getInstance().getReference("Users").child(userID).child("userPassword").setValue(Newpass);
                        FirebaseDatabase.getInstance().getReference("Users").child(userID).child("confPass").setValue(Newpass);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginPage.this,"This user not registered! "+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    } private void sendUserToNextActivity() {
        Intent intent=new Intent(LoginPage.this,HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }}
