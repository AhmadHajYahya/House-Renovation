package com.ahmad.houserenovationapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText HRA_ETXT_login_username;
    private TextInputEditText HRA_ETXT_login_password;
    private AppCompatButton HRA_BTN_login_submitButton;
    private TextView HRA_LINK_register_here;
    private Map<String,String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        data = new HashMap<>();
        findView();
        registerOnClick();
        submitLogin();
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
                // Todo
                // login
                boolean result = true; // check user info.
                if(result){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
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