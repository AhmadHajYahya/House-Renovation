package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.callback.RequestCallBack;
import com.ahmad.houserenovationapp.enums.Status;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.MyDataManager;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/** @noinspection ALL*/
public class CurrentJobFragment extends Fragment implements OnMapReadyCallback {

    private TextView HRA_TXT_currentJob_title;
    private TextView HRA_TXT_currentJob_description;
    private TextView HRA_TXT_currentJob_category;
    private TextView HRA_TXT_currentJob_customer_personalName;
    private TextView HRA_TXT_currentJob_customer_phoneNumber;
    private MapView HRA_MAP_currentJob_map;
    private AppCompatButton HRA_BTN_currentJob_finish;
    private AppCompatButton HRA_BTN_currentJob_startWorking;
    private MapHandler mapHandler;
    private RelativeLayout HRA_LAYOUT_currentJob_noJob_container;
    private RelativeLayout HRA_LAYOUT_currentJob_container;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_job, container, false);
        findViews(view);

        HRA_MAP_currentJob_map.onCreate(savedInstanceState);
        HRA_MAP_currentJob_map.getMapAsync(this);
        mapHandler = new MapHandler(getActivity(), HRA_MAP_currentJob_map, this);

        updateView();

        DataBaseManager.getWorkerCurrentJob(new RequestCallBack() {
            @Override
            public void onRequestRetrieved(Request request) {
                MyDataManager.setCurrentJob(request);
                if(MyDataManager.getCurrentJob()!=null && MyDataManager.getCurrentJob().getStatus().equals(Status.IN_PROGRESS)){
                    HRA_BTN_currentJob_startWorking.setVisibility(View.GONE);
                    HRA_BTN_currentJob_finish.setVisibility(View.VISIBLE);
                }else{
                    HRA_BTN_currentJob_startWorking.setVisibility(View.VISIBLE);
                    HRA_BTN_currentJob_finish.setVisibility(View.GONE);
                }
            }
        });

        startWorkingButtonListener();
        finishButtonListener();
        return view;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapHandler.setupMap(googleMap);
        if(MyDataManager.getCurrentJob()!=null){
            LatLng location = new LatLng(MyDataManager.getCurrentJob().getLatitude(),MyDataManager.getCurrentJob().getLongitude());
            mapHandler.getmMap().addMarker(new MarkerOptions().position(location));
            mapHandler.getmMap().moveCamera(CameraUpdateFactory.newLatLng(location));
            mapHandler.getmMap().animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    void finishButtonListener(){
        HRA_BTN_currentJob_finish.setOnClickListener(v->{
            DataBaseManager.updateRequest(MyDataManager.getCurrentJob().getId(), "status", Status.DONE.name());
            MyDataManager.setCurrentJob(null);
            DataBaseManager.updateUserData(DataBaseManager.getCurrentUser().getId(),"working",false);
            DataBaseManager.getUser(DataBaseManager::setCurrentUser);
            HRA_BTN_currentJob_startWorking.setVisibility(View.GONE);
            updateView();
            Toast.makeText(getActivity(),"Job Finished",Toast.LENGTH_SHORT).show();
        });
    }
    void startWorkingButtonListener(){
        HRA_BTN_currentJob_startWorking.setOnClickListener(v->{
            DataBaseManager.getWorkerCurrentJob(new RequestCallBack() {
                @Override
                public void onRequestRetrieved(Request request) {
                    if(request!=null){
                        DataBaseManager.updateRequest(request.getId(), "status", Status.IN_PROGRESS.name());
                        DataBaseManager.getWorkerCurrentJob(new RequestCallBack() {
                            @Override
                            public void onRequestRetrieved(Request request) {
                                MyDataManager.setCurrentJob(request);
                            }
                        });

                        HRA_BTN_currentJob_startWorking.setVisibility(View.GONE);
                        HRA_BTN_currentJob_finish.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Work Started",Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataManager.setCurrentJob(null);
                        HRA_LAYOUT_currentJob_noJob_container.setVisibility(View.VISIBLE);
                        HRA_LAYOUT_currentJob_container.setVisibility(View.GONE);
                    }
                }
            });


        });
    }


    void updateView(){
        if(MyDataManager.getCurrentJob()!=null){
            HRA_LAYOUT_currentJob_noJob_container.setVisibility(View.GONE);
            HRA_LAYOUT_currentJob_container.setVisibility(View.VISIBLE);
            HRA_TXT_currentJob_title.setText(getString(R.string.title, MyDataManager.getCurrentJob().getTitle()));
            HRA_TXT_currentJob_description.setText(getString(R.string.description, MyDataManager.getCurrentJob().getDescription()));
            HRA_TXT_currentJob_category.setText(getString(R.string.category, MyDataManager.getCurrentJob().getCategory()));
            HRA_TXT_currentJob_customer_personalName.setText(getString(R.string.personalName, MyDataManager.getCurrentJob().getCustomer().getPersonalName()));
            HRA_TXT_currentJob_customer_phoneNumber.setText(getString(R.string.phoneNumber, MyDataManager.getCurrentJob().getCustomer().getPhoneNumber()));
        }
        else{
            HRA_LAYOUT_currentJob_noJob_container.setVisibility(View.VISIBLE);
            HRA_LAYOUT_currentJob_container.setVisibility(View.GONE);
        }
    }
    void findViews( View view){

        HRA_TXT_currentJob_title = view.findViewById(R.id.HRA_TXT_currentJob_title);
        HRA_TXT_currentJob_description = view.findViewById(R.id.HRA_TXT_currentJob_description);
        HRA_TXT_currentJob_category = view.findViewById(R.id.HRA_TXT_currentJob_category);
        HRA_TXT_currentJob_customer_personalName = view.findViewById(R.id.HRA_TXT_currentJob_customer_personalName);
        HRA_TXT_currentJob_customer_phoneNumber = view.findViewById(R.id.HRA_TXT_currentJob_customer_phoneNumber);
        HRA_MAP_currentJob_map = view.findViewById(R.id.HRA_MAP_currentJob_map);
        HRA_BTN_currentJob_finish = view.findViewById(R.id.HRA_BTN_currentJob_finish);
        HRA_LAYOUT_currentJob_noJob_container = view.findViewById(R.id.HRA_LAYOUT_currentJob_noJob_container);
        HRA_LAYOUT_currentJob_container= view.findViewById(R.id.HRA_LAYOUT_currentJob_container);
        HRA_BTN_currentJob_startWorking= view.findViewById(R.id.HRA_BTN_currentJob_startWorking);

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
