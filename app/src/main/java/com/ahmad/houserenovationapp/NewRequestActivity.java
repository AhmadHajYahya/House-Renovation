package com.ahmad.houserenovationapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.GeneratorClass;
import com.ahmad.houserenovationapp.logic.MyDataManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class NewRequestActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView HRA_TXT_newRequest_category;
    private AppCompatEditText HRA_ETXT_newRequest_title;
    private AppCompatEditText HRA_ETXT_newRequest_description;
    private AppCompatButton HRA_BTN_newRequest;
    private AppCompatButton HRA_BTN_newRequest_cancel;
    private MapView HRA_MAP_newRequest_map;
    private String selectedCategory;
    private MapHandler mapHandler;
    private Marker selectedLocationMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        findViews();
        HRA_MAP_newRequest_map.onCreate(savedInstanceState);
        HRA_MAP_newRequest_map.getMapAsync(this);
        mapHandler = new MapHandler(this, HRA_MAP_newRequest_map, this);

        HRA_TXT_newRequest_category.setText("Category: " + MyDataManager.getWorkerToHire().getWorkCategory());
        submitRequestButtonListener();
        cancelButtonListener();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapHandler.setupMap(googleMap);
        // Set a click listener for the map
        mapHandler.getmMap().setOnMapClickListener(latLng -> {
            if (selectedLocationMarker != null) {
                selectedLocationMarker.remove();
            }

            selectedLocationMarker = mapHandler.getmMap().addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            selectedLocationMarker.showInfoWindow();
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    void submitRequestButtonListener(){

        // Handle Request Service button click
        HRA_BTN_newRequest.setOnClickListener(v -> {
            String title = HRA_ETXT_newRequest_title.getText().toString().trim();
            String description = HRA_ETXT_newRequest_description.getText().toString().trim();
            selectedLocationMarker = mapHandler.getSelectedLocationMarker();
            LatLng currentLocation;
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show();
            } else if (selectedLocationMarker == null) {
                currentLocation = mapHandler.getCurrentLocation();
                submitServiceRequest(title, description, currentLocation);
            } else {
                LatLng selectedLocation = selectedLocationMarker.getPosition();
                // Handle service request submission with title, description, and selected location
                submitServiceRequest(title, description, selectedLocation);
            }
        });
    }
    void cancelButtonListener(){
        HRA_BTN_newRequest_cancel.setOnClickListener(v -> {
            MyDataManager.setWorkerToHire(null);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }
    private void submitServiceRequest(String title, String description, LatLng selectedLocation) {
        Map<String,Object> data = new HashMap<>();

        data.put("title",title);
        data.put("description",description);
        data.put("location",selectedLocation);
        data.put("latitude",selectedLocation.latitude);
        data.put("longitude",selectedLocation.longitude);
        data.put("category",MyDataManager.getWorkerToHire().getWorkCategory());
        data.put("customer", DataBaseManager.getCurrentUser());
        data.put("worker",MyDataManager.getWorkerToHire());

        Request request = GeneratorClass.createService(data);
        DataBaseManager.saveRequest(request);

        MyDataManager.setWorkerToHire(null);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Service Requested Successfully", Toast.LENGTH_SHORT).show();
    }


    void findViews(){
        HRA_ETXT_newRequest_title =findViewById(R.id.HRA_ETXT_newRequest_title);
        HRA_ETXT_newRequest_description = findViewById(R.id.HRA_ETXT_newRequest_description);
        HRA_BTN_newRequest = findViewById(R.id.HRA_BTN_newRequest);
        HRA_MAP_newRequest_map = findViewById(R.id.HRA_MAP_newRequest_map);
        HRA_BTN_newRequest_cancel = findViewById(R.id.HRA_BTN_newRequest_cancel);
        HRA_TXT_newRequest_category = findViewById(R.id.HRA_TXT_newRequest_category);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHandler.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapHandler.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapHandler.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapHandler.onLowMemory();
    }
}