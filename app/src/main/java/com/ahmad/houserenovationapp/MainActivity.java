package com.ahmad.houserenovationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.fragments.FavoriteFragment;
import com.ahmad.houserenovationapp.fragments.HomeFragment;
import com.ahmad.houserenovationapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView HRA_NAV_main_bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the default fragment (HomeFragment) when the activity is first created
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.HRA_FRAGMENT_main_fragment_container, new HomeFragment()).commit();
        }
        findViews();

        navigatorListener();

    }


    void navigatorListener(){
        this.HRA_NAV_main_bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.navigation_favorite) {
                    selectedFragment = new FavoriteFragment();
                } else if (id == R.id.navigation_profile) {
                    selectedFragment = new ProfileFragment();
                }

                // Replace the currently displayed fragment with the selected fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.HRA_FRAGMENT_main_fragment_container, selectedFragment).commit();

                return true;
            }
        });
    }


    void findViews(){
        this.HRA_NAV_main_bottom_navigation = findViewById(R.id.HRA_NAV_main_bottom_navigation);
    }


}