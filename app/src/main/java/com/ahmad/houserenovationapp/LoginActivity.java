package com.ahmad.houserenovationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

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
        if(this.HRA_ETXT_login_username.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Username is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            this.data.put("username", this.HRA_ETXT_login_username.getText().toString().trim());
        }
        if(this.HRA_ETXT_login_password.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Password is empty.", Toast.LENGTH_SHORT).show();
            return ;
        }
        else{
            this.data.put("password",this.HRA_ETXT_login_password.getText().toString().trim());
        }
    }

    void submitLogin(){
        this.HRA_BTN_login_submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputData();
                if(!data.isEmpty()){
                    // Todo
                    // login

                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void registerOnClick(){
        this.HRA_LINK_register_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    void findView(){
        this.HRA_LINK_register_here = findViewById(R.id.HRA_LINK_register_here);
        this.HRA_ETXT_login_username = findViewById(R.id.HRA_ETXT_login_username);
        this.HRA_ETXT_login_password = findViewById(R.id.HRA_ETXT_login_password);
        this.HRA_BTN_login_submitButton = findViewById(R.id.HRA_BTN_login_submitButton);
    }
}