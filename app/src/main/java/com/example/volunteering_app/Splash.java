package com.example.volunteering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_DELAY_MS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Jump to the profile activity after the delay
                Intent intent = new Intent(Splash.this, Profile.class);
                startActivity(intent);
                finish(); // Finish the splash activity to prevent returning back to it
            }
        }, SPLASH_DELAY_MS);
    }
}