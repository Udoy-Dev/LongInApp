package com.example.longinapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //-----------app bar color code start-----------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
        }
        //-----------app bar color code finish-----------------


        // গ্লোবাল ভেরিয়েবল ছাড়াই সরাসরি এভাবে লিখুন
        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // আপনার পরবর্তী কাজ এখানে লিখুন
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // ৩ সেকেন্ড দেরি














        //-------------------------------------
    }
        //-------------------------------------





















}