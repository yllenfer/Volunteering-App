package com.example.volunteering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // User is already signed in, go to Profile activity
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
            finish();
        } else {
            // User is not signed in, go to Register or Login activity
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
            finish();
        }
    }
}