package com.ahmad.houserenovationapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.GeneratorClass;
import com.ahmad.houserenovationapp.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private Spinner HRA_SP_register_categorySpinner;
    private LinearLayout HRA_LAYOUT_register_workerLayout;
    private LinearLayout HRA_LAYOUT_register_customerLayout;
    private TextInputEditText HRA_ETXT_register_username;
    private TextInputEditText HRA_ETXT_register_name;
    private TextInputEditText HRA_ETXT_register_password;
    private TextInputEditText HRA_ETXT_register_phoneNumber;
    private TextInputEditText HRA_ETXT_register_address;
    private AppCompatButton HRA_BTN_register_submitButton;
    private UserType userType;
    private String selectedCategory;

    private Map<String,String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        data = new HashMap<>();
        findViews();

        selectUserType();
        spinnerSelect();
        submitRegister();
    }


    void getInputData(){
        if(Objects.requireNonNull(this.HRA_ETXT_register_username.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Username is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Objects.requireNonNull(this.HRA_ETXT_register_name.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Personal name is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Objects.requireNonNull(this.HRA_ETXT_register_password.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Password is empty.", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(Objects.requireNonNull(this.HRA_ETXT_register_phoneNumber.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Phone number is empty.", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(Objects.requireNonNull(this.HRA_ETXT_register_address.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Address is empty.", Toast.LENGTH_SHORT).show();
            return ;
        }
        this.data.put("username", this.HRA_ETXT_register_username.getText().toString().trim());
        this.data.put("personalName", this.HRA_ETXT_register_name.getText().toString().trim());
        this.data.put("phoneNumber",this.HRA_ETXT_register_password.getText().toString().trim());
        this.data.put("address",this.HRA_ETXT_register_phoneNumber.getText().toString().trim());
        this.data.put("password",this.HRA_ETXT_register_address.getText().toString().trim());
        if(this.userType.equals(UserType.WORKER)){
            this.data.put("category",this.selectedCategory);
        }
        else{
            this.data.remove("category");
        }
    }

    void submitRegister(){
        this.HRA_BTN_register_submitButton.setOnClickListener(v -> {
            getInputData();
            if(!data.isEmpty()){
                // Todo
                // register user
                User user = null;
                if(this.userType.equals(UserType.WORKER)){
                    user = GeneratorClass.createWorker(this.data);
                }else{
                    user = GeneratorClass.createCustomer(this.data);
                }
                if(user != null){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void selectUserType(){
        HRA_LAYOUT_register_workerLayout.setOnClickListener(v -> selectWorker());

        HRA_LAYOUT_register_customerLayout.setOnClickListener(v -> selectCustomer());

        // Initially select the worker option or none
        selectWorker();
    }

    private void selectWorker() {
        HRA_LAYOUT_register_workerLayout.setBackgroundColor(Color.parseColor("#FFD700")); // Set selected color
        HRA_LAYOUT_register_customerLayout.setBackgroundColor(Color.parseColor("#FFFFFF")); // Set unselected color
        HRA_SP_register_categorySpinner.setVisibility(View.VISIBLE); // Show the Spinner
        this.userType = UserType.WORKER;
    }

    private void selectCustomer() {
        HRA_LAYOUT_register_customerLayout.setBackgroundColor(Color.parseColor("#FFD700")); // Set selected color
        HRA_LAYOUT_register_workerLayout.setBackgroundColor(Color.parseColor("#FFFFFF")); // Set unselected color
        HRA_SP_register_categorySpinner.setVisibility(View.GONE); // Hide the Spinner
        this.userType = UserType.CUSTOMER;
    }


    void spinnerSelect(){
        this.HRA_SP_register_categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected (optional)
            }
        });
    }




    void findViews(){
        this.HRA_SP_register_categorySpinner = findViewById(R.id.HRA_SP_register_categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HRA_SP_register_categorySpinner.setAdapter(adapter);


        HRA_LAYOUT_register_workerLayout = findViewById(R.id.HRA_LAYOUT_register_workerLayout);
        HRA_LAYOUT_register_customerLayout = findViewById(R.id.HRA_LAYOUT_register_customerLayout);


        HRA_ETXT_register_username = findViewById(R.id.HRA_ETXT_register_username);
        HRA_ETXT_register_name = findViewById(R.id.HRA_ETXT_register_name);
        HRA_ETXT_register_password = findViewById(R.id.HRA_ETXT_register_password);
        HRA_ETXT_register_phoneNumber = findViewById(R.id.HRA_ETXT_register_phoneNumber);
        HRA_ETXT_register_address = findViewById(R.id.HRA_ETXT_register_address);
        HRA_BTN_register_submitButton = findViewById(R.id.HRA_BTN_register_submitButton);
    }
}