package com.example.keyasthma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.keyasthma.databinding.ActivityAboutUsPageBinding;
import com.example.keyasthma.databinding.ActivityAccountPageBinding;
import com.example.keyasthma.databinding.ActivityContactDoctorPageBinding;

public class ContactDoctorPage extends DrawerBaseActivity {
    ActivityContactDoctorPageBinding activityContactDoctorPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContactDoctorPageBinding = ActivityContactDoctorPageBinding.inflate(getLayoutInflater());
        setContentView(activityContactDoctorPageBinding.getRoot());
        allocateActivityTitle("CONTACT DOCTOR");

    }
    }