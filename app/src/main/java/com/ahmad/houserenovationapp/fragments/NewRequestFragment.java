package com.ahmad.houserenovationapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/** @noinspection ALL*/
public class NewRequestFragment extends Fragment implements OnMapReadyCallback {

    private Spinner HRA_SP_newRequest_spinner;
    private AppCompatEditText HRA_ETXT_newRequest_title;
    private AppCompatEditText HRA_ETXT_newRequest_description;
    private AppCompatButton HRA_BTN_newRequest;
    private MapView HRA_MAP_newRequest_map;
    private String selectedCategory;
    private MapHandler mapHandler;
    private Marker selectedLocationMarker;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_request, container, false);

        findViews(view);

        spinnerSelect();

        HRA_MAP_newRequest_map.onCreate(savedInstanceState);
        HRA_MAP_newRequest_map.getMapAsync(this);
        mapHandler = new MapHandler(getActivity(), HRA_MAP_newRequest_map, this);
        submitRequestButtonListener();


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    void spinnerSelect(){
        this.HRA_SP_newRequest_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    void submitRequestButtonListener(){

        // Handle Request Service button click
        HRA_BTN_newRequest.setOnClickListener(v -> {
            String title = HRA_ETXT_newRequest_title.getText().toString().trim();
            String description = HRA_ETXT_newRequest_description.getText().toString().trim();
            selectedLocationMarker = mapHandler.getSelectedLocationMarker();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter title and description", Toast.LENGTH_SHORT).show();
            } else if (selectedLocationMarker == null) {
                Toast.makeText(getActivity(), "Please select a location on the map", Toast.LENGTH_SHORT).show();
            } else {
                LatLng selectedLocation = selectedLocationMarker.getPosition();
                // Handle service request submission with title, description, and selected location
                submitServiceRequest(title, description, selectedLocation);
            }
        });
    }
    private void submitServiceRequest(String title, String description, LatLng selectedLocation) {
        // Handle the service request submission logic here with the title, description, and selected location
        Toast.makeText(getActivity(), "Service Requested: " + title + " at " + selectedLocation.toString(), Toast.LENGTH_SHORT).show();
    }


    void findViews(View view){
        // Initialize Views
        HRA_SP_newRequest_spinner = view.findViewById(R.id.HRA_SP_newRequest_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HRA_SP_newRequest_spinner.setAdapter(adapter);
        HRA_ETXT_newRequest_title = view.findViewById(R.id.HRA_ETXT_newRequest_title);
        HRA_ETXT_newRequest_description = view.findViewById(R.id.HRA_ETXT_newRequest_description);
        HRA_BTN_newRequest = view.findViewById(R.id.HRA_BTN_newRequest);
        HRA_MAP_newRequest_map = view.findViewById(R.id.HRA_MAP_newRequest_map);
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
