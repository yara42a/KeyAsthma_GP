package com.example.keyasthma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.keyasthma.databinding.ActivityAboutUsPageBinding;
import com.example.keyasthma.databinding.ActivityAccountPageBinding;

public class AboutUsPage extends DrawerBaseActivity {
    ActivityAboutUsPageBinding activityAboutUsPageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutUsPageBinding = ActivityAboutUsPageBinding.inflate(getLayoutInflater());
        setContentView(activityAboutUsPageBinding.getRoot());
        allocateActivityTitle("ABOUT US");
    }
}
