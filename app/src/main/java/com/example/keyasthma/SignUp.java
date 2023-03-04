
package com.example.keyasthma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://key-asthma-default-rtdb.firebaseio.com");

    EditText PersonName, EmailAddress, Password, ConfPassword;
    ProgressBar progressBar;
    Button SignUp_btn2;
    TextView Login_btn2;
    ImageButton Back;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseAuth mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        PersonName = findViewById(R.id.PersonName);
        EmailAddress = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.Password);
        ConfPassword = findViewById(R.id.ConfPassword);
        progressBar = findViewById(R.id.progressBar);
        SignUp_btn2 = findViewById(R.id.SignUp_btn2);
        Login_btn2 = findViewById(R.id.Login_btn2);
        Back=findViewById(R.id.back);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Welcome.class));
            }
        });

        Login_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, LoginPage.class));
            }
        });

        SignUp_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforAuth();

            }
        });
    }

    private void perforAuth() {
        String personNameTxt = PersonName.getText().toString();
        String EmailAddTxt = EmailAddress.getText().toString();
        String  PassTxt = Password.getText().toString();
        String ConfPassTxt = ConfPassword.getText().toString();

        if(!EmailAddTxt.matches(emailPattern)){
            EmailAddress.setError("Enter Context Email");}
        else if(PassTxt.isEmpty() || PassTxt.length()<8){
            Password.setError("The password should be greater than 8");
        }else if(!PassTxt.equals(ConfPassTxt)){
            ConfPassword.setError("Password Not match Both field");
        }else{
            progressDialog.setMessage("Please wait while Registration....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(EmailAddTxt,PassTxt)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(personNameTxt ,PassTxt ,EmailAddTxt , ConfPassTxt);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);

                        progressDialog.dismiss();

                        Toast.makeText(SignUp.this,"registration successfully",
                                Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this,"User failed to Registration!! "
                                +task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(SignUp.this,LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}