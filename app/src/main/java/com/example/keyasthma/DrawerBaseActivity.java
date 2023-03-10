package com.example.keyasthma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;

    @Override
    public void setContentView(View view){
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()){
            case R.id.nav_Home:
                startActivity(new Intent(this, HomePage.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_Account:
                startActivity(new Intent(this, AccountPage.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_GAdv:
                startActivity(new Intent(this, GeneralAdvicePage.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_Contact:
                startActivity(new Intent(this, ContactDoctorPage.class));
                overridePendingTransition(0,0);
                break;

            case R.id.nav_AbtUs:
                startActivity(new Intent(this, AboutUsPage.class));
                overridePendingTransition(0,0);
                break;
            case R.id.nav_LogOut:
                Intent intent=new Intent(DrawerBaseActivity.this,Welcome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
                    }
        return false;
    }
    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar() !=null) {
            getSupportActionBar().setTitle(titleString);
        }
    }

}