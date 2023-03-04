package com.example.keyasthma;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.example.keyasthma.databinding.ActivityAccountPageBinding;
import com.example.keyasthma.databinding.ActivityHomePageBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountPage extends DrawerBaseActivity {
    Button logout_btn,SignUp_btn2;
    EditText UserEmail,UserName;
    TextView Email,Name;

    FirebaseAuth mAuth;
    ActivityAccountPageBinding activityAccountPageBinding;

    FirebaseUser user;
    DatabaseReference reference;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAccountPageBinding = ActivityAccountPageBinding.inflate(getLayoutInflater());
        setContentView(activityAccountPageBinding.getRoot());
        allocateActivityTitle("ACCOUNT");
        UserName=findViewById(R.id.YourName);
        UserEmail=findViewById(R.id.YourEmail);
        Email=findViewById(R.id.Email);
        Name=findViewById(R.id.Name);
        SignUp_btn2=findViewById(R.id.SignUp_btn2);
        logout_btn=findViewById(R.id.logout_btn);
        mAuth=FirebaseAuth.getInstance();

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAuth.signOut();
                signOutUser();
//                Intent intent=new Intent(AccountPage.this,LoginPage.class);
//                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null)
                {
                    String name = userProfile.userName;
                    String email = userProfile.email;

                    UserName.setText(name);
                    UserEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountPage.this , "Something Wrong happened!",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void signOutUser() {
        Intent intent=new Intent(AccountPage.this,LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}