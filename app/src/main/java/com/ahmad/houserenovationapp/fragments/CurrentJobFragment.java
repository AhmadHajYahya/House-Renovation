package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

public class CurrentJobFragment extends Fragment implements OnMapReadyCallback {

    private TextView HRA_TXT_currentJob_title;
    private TextView HRA_TXT_currentJob_description;
    private TextView HRA_TXT_currentJob_category;
    private TextView HRA_TXT_currentJob_customer_personalName;
    private TextView HRA_TXT_currentJob_customer_phoneNumber;
    private MapView HRA_MAP_currentJob_map;
    private AppCompatButton HRA_BTN_currentJob_finish;
    private AppCompatButton HRA_BTN_currentJob_cancel;
    private MapHandler mapHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_job, container, false);
        findViews(view);

        HRA_MAP_currentJob_map.onCreate(savedInstanceState);
        HRA_MAP_currentJob_map.getMapAsync(this);
        mapHandler = new MapHandler(getActivity(), HRA_MAP_currentJob_map, this);

        finishButtonListener();
        cancelButtonListener();
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    void finishButtonListener(){
        HRA_BTN_currentJob_finish.setOnClickListener(v->{
            Toast.makeText(getActivity(),"Job Finished",Toast.LENGTH_SHORT).show();
        });
    }
    void cancelButtonListener(){
        HRA_BTN_currentJob_cancel.setOnClickListener(v->{
            Toast.makeText(getActivity(),"Job Canceled",Toast.LENGTH_SHORT).show();
        });
    }
    void findViews( View view){

        HRA_TXT_currentJob_title = view.findViewById(R.id.HRA_TXT_currentJob_title);
        HRA_TXT_currentJob_description = view.findViewById(R.id.HRA_TXT_currentJob_description);
        HRA_TXT_currentJob_category = view.findViewById(R.id.HRA_TXT_currentJob_category);
        HRA_TXT_currentJob_customer_personalName = view.findViewById(R.id.HRA_TXT_currentJob_customer_personalName);
        HRA_TXT_currentJob_customer_phoneNumber = view.findViewById(R.id.HRA_TXT_currentJob_customer_phoneNumber);
        HRA_MAP_currentJob_map = view.findViewById(R.id.HRA_MAP_currentJob_map);
        HRA_BTN_currentJob_finish = view.findViewById(R.id.HRA_BTN_currentJob_finish);
        HRA_BTN_currentJob_cancel = view.findViewById(R.id.HRA_BTN_currentJob_cancel);

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
