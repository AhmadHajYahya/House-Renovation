package com.ahmad.houserenovationapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.fragments.CurrentJobFragment;
import com.ahmad.houserenovationapp.fragments.NewRequestFragment;
import com.ahmad.houserenovationapp.fragments.HomeFragment;
import com.ahmad.houserenovationapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView HRA_NAV_main_customer_bottom_navigation;
    private BottomNavigationView HRA_NAV_main_worker_bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the default fragment (HomeFragment) when the activity is first created
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HRA_FRAGMENT_main_fragment_container, new HomeFragment()).commit();
        }
        findViews();
        HRA_NAV_main_customer_bottom_navigation.setVisibility(View.GONE);
        HRA_NAV_main_worker_bottom_navigation.setVisibility(View.VISIBLE);
        customerNavigatorListener();
        workerNavigatorListener();

    }


    void customerNavigatorListener(){
        this.HRA_NAV_main_customer_bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.navigation_add_request) {
                    selectedFragment = new NewRequestFragment();
                } else if (id == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                // Replace the currently displayed fragment with the selected fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.HRA_FRAGMENT_main_fragment_container, selectedFragment).commit();

                return true;
            }
        });
    }
    void workerNavigatorListener(){
        this.HRA_NAV_main_worker_bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.worker_navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.worker_navigation_current_job) {
                    selectedFragment = new CurrentJobFragment();
                } else if (id == R.id.worker_navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                // Replace the currently displayed fragment with the selected fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.HRA_FRAGMENT_main_fragment_container, selectedFragment).commit();

                return true;
            }
        });
    }

    void findViews(){
        this.HRA_NAV_main_customer_bottom_navigation = findViewById(R.id.HRA_NAV_main_customer_bottom_navigation);
        this.HRA_NAV_main_worker_bottom_navigation = findViewById(R.id.HRA_NAV_main_worker_bottom_navigation);
    }


}