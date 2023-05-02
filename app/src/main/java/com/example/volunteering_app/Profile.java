package com.example.volunteering_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;


public class Profile extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private TextView textViewCity; // Variable to reference the city TextView
    private TextView textViewCountry; // Variable to reference the country TextView

    private ImageView profilePh;

    private void loadProfilePicture() {
        SharedPreferences sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
        String profilePictureUrl = sharedPreferences.getString("profilePictureUrl", null);

        if (profilePictureUrl != null) {
            Glide.with(this)
                    .load(profilePictureUrl)
                    .centerCrop()
                    .into(profilePh);
        } else {
            // Handle the case where the profile picture URL is null
            // For example, display a default image or show an error message
        }
    }








    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                // Permission denied
            }
        }
    }


    private FusedLocationProviderClient fusedLocationClient;

    private void getLastKnownLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            getAddressFromLocation(latitude, longitude);
                        }
                    });
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        // Check if the Geocoder is available
        if (!Geocoder.isPresent()) {
            // Handle the case where the Geocoder is not available
            System.out.println("Here  I am");
            return;
        }



        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String city = address.getLocality();
                String country = address.getCountryName();


                // Check if the city and country are not null
                if (city != null && country != null) {
                    // Display the city and country in the TextViews
                    textViewCity.setText(city + ", ");
                    textViewCountry.setText(country);
                } else {
                    System.out.println("City is null");
                }
            } else {
                // Handle the case where the Geocoder returns no results
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        requestLocationPermissions();
        loadProfilePicture();

        profilePh = findViewById(R.id.profilePhoto);
        Intent intent = getIntent();
        String profilePictureUrl = intent.getStringExtra("profilePictureUrl");

        // Load the profile picture into the ImageView using Glide
        if (profilePictureUrl != null) {
            // Load the profile picture into the ImageView using Glide
            Glide.with(this)
                    .load(profilePictureUrl)
                    .centerCrop()
                    .into(profilePh);
        } else {
            // Handle the case where the profile picture URL is null
            // For example, display a default image or show an error message
        }






//        This part is for the location
        textViewCity = findViewById(R.id.cityLocation);
        textViewCountry = findViewById(R.id.countryLocation);

//        This part is for the volunteering button image
        ImageButton myVolunteerButton = findViewById(R.id.volunteerButton);
        myVolunteerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Volunteer.class);
                startActivity(intent);
            }
        });


        ImageButton mySettings = findViewById(R.id.settingsButton);
        mySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ProfileSettings.class);
                startActivity(intent);
            }
        });


// This is for the FAQs Button
        ImageButton myFaqsButton = findViewById(R.id.faqButton);
        myFaqsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Faqs.class);
                startActivity(intent);
            }
        });

        ImageButton profileBackButton = findViewById(R.id.profileExit);
        profileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity and return to the previous activity
                finish();
            }
        });







// This last two brackets are from the class
    }
}