package com.example.volunteering_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Register extends AppCompatActivity {

    TextInputEditText textRegisterEmail, textRegisterPassword;
    ImageButton regButton;
    FirebaseAuth mAuth;
    CallbackManager callbackManager;
    ImageButton fbButton;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FacebookSdk.sdkInitialize(getApplicationContext());


        textRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        textRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        regButton = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();

        //            This is for the facebook register/ login
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(Register.this, Profile.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });


        fbButton = findViewById(R.id.faceBookButton);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Register.this, Arrays.asList("public_profile"));
            }
        });






    regButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email, password;
            email = String.valueOf(textRegisterEmail.getText());
            password = String.valueOf(textRegisterPassword.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;

            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "Nothing Happened!.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    });




        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

}
}