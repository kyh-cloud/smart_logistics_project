package com.example.itsea;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    Handler splash = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splash .postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splash_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(splash_intent);

                finish();
            }
        },1000);
    }
}