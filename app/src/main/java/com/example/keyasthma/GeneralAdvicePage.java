package com.example.keyasthma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.keyasthma.databinding.ActivityAccountPageBinding;
import com.example.keyasthma.databinding.ActivityGeneralAdvicePageBinding;

public class GeneralAdvicePage extends DrawerBaseActivity {
    ActivityGeneralAdvicePageBinding activityGeneralAdvicePageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGeneralAdvicePageBinding = ActivityGeneralAdvicePageBinding.inflate(getLayoutInflater());
        setContentView(activityGeneralAdvicePageBinding.getRoot());
        allocateActivityTitle("GENERAL ADVICE");
    }
}