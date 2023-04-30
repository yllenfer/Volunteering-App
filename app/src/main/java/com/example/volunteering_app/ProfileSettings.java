package com.example.volunteering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSettings extends AppCompatActivity {

    FirebaseAuth auth;
//    ImageButton buttonLogout;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        auth = FirebaseAuth.getInstance();
        AppCompatImageButton buttonLogout = findViewById(R.id.logoutButton);

        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            //Don't know what to do here
        }

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();

            }
        });





    }

    private void logout() {
        // Log out from Firebase (and/or Facebook, if applicable)
        FirebaseAuth.getInstance().signOut();

        // Create an intent to start the login activity
        Intent intent = new Intent(this, ProfileSettings.class);

        // Set the appropriate intent flags to clear the back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Start the login activity
        startActivity(intent);

        // Finish the current activity (optional, depending on your use case)
        finish();
    }




}