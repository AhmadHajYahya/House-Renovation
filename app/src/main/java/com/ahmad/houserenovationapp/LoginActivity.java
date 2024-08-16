package com.ahmad.houserenovationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText HRA_ETXT_login_username;
    private TextInputEditText HRA_ETXT_login_password;
    private AppCompatButton HRA_BTN_login_submitButton;
    private TextView HRA_LINK_register_here;
    private Map<String,String> data;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        data = new HashMap<>();
        findView();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the login activity
        } else {
            // User is not logged in, proceed with login process
            registerOnClick();
            submitLogin();
        }
    }

    void getInputData(){
        if(Objects.requireNonNull(this.HRA_ETXT_login_username.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Username is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Objects.requireNonNull(this.HRA_ETXT_login_password.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Password is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        this.data.put("username", this.HRA_ETXT_login_username.getText().toString().trim());
        this.data.put("password",this.HRA_ETXT_login_password.getText().toString().trim());
    }

    void submitLogin(){
        this.HRA_BTN_login_submitButton.setOnClickListener(v -> {
            getInputData();
            if(!data.isEmpty()){
                mAuth.signInWithEmailAndPassword(data.get("username"), data.get("password"))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Login successful, navigate to main activity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    void registerOnClick(){
        this.HRA_LINK_register_here.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    void findView(){
        this.HRA_LINK_register_here = findViewById(R.id.HRA_LINK_register_here);
        this.HRA_ETXT_login_username = findViewById(R.id.HRA_ETXT_login_username);
        this.HRA_ETXT_login_password = findViewById(R.id.HRA_ETXT_login_password);
        this.HRA_BTN_login_submitButton = findViewById(R.id.HRA_BTN_login_submitButton);
    }
}