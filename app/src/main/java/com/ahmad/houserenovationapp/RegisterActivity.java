package com.ahmad.houserenovationapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.GeneratorClass;
import com.ahmad.houserenovationapp.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/** @noinspection ALL*/
public class RegisterActivity extends AppCompatActivity {
    private Spinner HRA_SP_register_categorySpinner;
    private LinearLayout HRA_LAYOUT_register_workerLayout;
    private LinearLayout HRA_LAYOUT_register_customerLayout;
    private TextInputEditText HRA_ETXT_register_email;
    private TextInputEditText HRA_ETXT_register_name;
    private TextInputEditText HRA_ETXT_register_password;
    private TextInputEditText HRA_ETXT_register_phoneNumber;
    private AppCompatButton HRA_BTN_register_submitButton;
    private UserType userType ;
    private String selectedCategory;

    private Map<String,Object> data;
    private FirebaseAuth mAuth;

    private FusedLocationProviderClient fusedLocationClient;
    private Location userLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        data = new HashMap<>();
        findViews();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();

        selectUserType();
        spinnerSelect();
        submitRegister();

    }

    private void getCurrentLocation() {
        // Request location permission if not already granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        userLocation = location;
                    }
                }
            });
        }
    }

    void getInputData(){
        if(Objects.requireNonNull(this.HRA_ETXT_register_email.getText()).toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Email is empty.", Toast.LENGTH_SHORT).show();
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
        this.data.put("email", this.HRA_ETXT_register_email.getText().toString().trim());
        this.data.put("personalName", this.HRA_ETXT_register_name.getText().toString().trim());
        this.data.put("password",this.HRA_ETXT_register_password.getText().toString().trim());
        this.data.put("phoneNumber",this.HRA_ETXT_register_phoneNumber.getText().toString().trim());
        this.data.put("latitude",userLocation.getLatitude());
        this.data.put("longitude",userLocation.getLongitude());
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
                mAuth.createUserWithEmailAndPassword((String) data.get("email"),(String) data.get("password"))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                    data.put("userId", userId);

                                    User user = null;
                                    if(userType.equals(UserType.WORKER)){
                                        user = GeneratorClass.createWorker(data);
                                    } else {
                                        user = GeneratorClass.createCustomer(data);
                                    }

                                    if(user != null){
                                        // Add user to database
                                        DataBaseManager.saveUser(user);
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


    void selectUserType(){
        HRA_LAYOUT_register_workerLayout.setOnClickListener(v -> selectWorker());

        HRA_LAYOUT_register_customerLayout.setOnClickListener(v -> selectCustomer());

        // Initially select the worker option or none
        selectCustomer();
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
        List<String> categoryList = new ArrayList<>();
        Category[] categories = Category.values();
        for (int i = 1; i < categories.length; i++) {
            categoryList.add(categories[i].name());
        }

// Create ArrayAdapter using the list of enum values
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HRA_SP_register_categorySpinner.setAdapter(adapter);


        HRA_LAYOUT_register_workerLayout = findViewById(R.id.HRA_LAYOUT_register_workerLayout);
        HRA_LAYOUT_register_customerLayout = findViewById(R.id.HRA_LAYOUT_register_customerLayout);


        HRA_ETXT_register_email = findViewById(R.id.HRA_ETXT_register_email);
        HRA_ETXT_register_name = findViewById(R.id.HRA_ETXT_register_name);
        HRA_ETXT_register_password = findViewById(R.id.HRA_ETXT_register_password);
        HRA_ETXT_register_phoneNumber = findViewById(R.id.HRA_ETXT_register_phoneNumber);
        HRA_BTN_register_submitButton = findViewById(R.id.HRA_BTN_register_submitButton);
    }
}