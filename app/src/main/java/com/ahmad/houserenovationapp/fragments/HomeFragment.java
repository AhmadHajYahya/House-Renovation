package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.CustomerRequestAdapter;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.utils.HelperFunctions;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private LinearLayout HRA_LAYOUT_home_categoryButtonContainer;
    private Button selectedButton = null; // Variable to store the selected button
    private RecyclerView HRA_VIEW_home_recyclerView;
    private AppCompatImageView HRA_IMG_home_mapIcon;
    private RelativeLayout HRA_LAYOUT_home_requestsLayout;
    private MapView HRA_VIEW_home_map_view;
    private CustomerRequestAdapter customerRequestAdapter;

    private boolean mapFlag = false;
    private MapHandler mapHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(view);
        createHorizontalScrollViewButtons();
        setupRecyclerView();
        mapIconListener();

        HRA_VIEW_home_map_view.onCreate(savedInstanceState);
        HRA_VIEW_home_map_view.getMapAsync(this);
        mapHandler = new MapHandler(getActivity(), HRA_VIEW_home_map_view, this);
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    void createHorizontalScrollViewButtons() {
        for (Category category : Category.values()) {
            Button button = new Button(getActivity());

            // Set the formatted text
            button.setText(HelperFunctions.formatCategoryName(category));

            // Apply the rounded background
            button.setBackgroundResource(R.drawable.rounded_button);

            // Set padding and text color
            button.setPadding(16, 16, 16, 16);
            button.setTextColor(getResources().getColor(android.R.color.black));

            // Set layout parameters with margin end
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(16); // Set margin end to 16dp
            button.setLayoutParams(params);

            // Set different background color for the "All" button
            if (category == Category.ALL && selectedButton == null) {
                button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
                button.setTextColor(getResources().getColor(android.R.color.white));
                selectedButton = button; // Store the "All" button as initially selected
            }

            // Add button to the container
            HRA_LAYOUT_home_categoryButtonContainer.addView(button);

            // Set click listeners on the buttons
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click based on category
                    if (selectedButton != null) {
                        // Reset the previously selected button to its original state
                        selectedButton.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
                        selectedButton.setTextColor(getResources().getColor(android.R.color.black));
                    }

                    // Highlight the clicked button
                    button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_green_dark));
                    button.setTextColor(getResources().getColor(android.R.color.white));
                    selectedButton = button; // Store the currently selected button
                }
            });
        }
    }

    void setupRecyclerView() {
        List<Request> requests = new ArrayList<>();
        requests.add(new Request("1", "Garden", "Cut Grass", Category.GARDNER, LocalDate.now(), null, null));
        requests.add(new Request("2", "Electric", "Electricty problem", Category.ELECTRICIAN, LocalDate.now(), null, null));
        requests.add(new Request("3", "Garden", "Cut Grass", Category.GARDNER, LocalDate.now(), null, null));
        requests.add(new Request("4", "Electric", "Electricty problem", Category.ELECTRICIAN, LocalDate.now(), null, null));
        requests.add(new Request("5", "Garden", "Cut Grass", Category.GARDNER, LocalDate.now(), null, null));
        requests.add(new Request("6", "Electric", "Electricty problem", Category.ELECTRICIAN, LocalDate.now(), null, null));

        HRA_VIEW_home_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerRequestAdapter = new CustomerRequestAdapter(getActivity(), requests,new CustomerRequestAdapter.OnRequestCancelListener() {
            @Override
            public void onRequestCancel(Request request, int position) {

                // TODO
                requests.remove(position);
                customerRequestAdapter.notifyItemRemoved(position);

            }
        });
        HRA_VIEW_home_recyclerView.setAdapter(customerRequestAdapter);
    }

    void mapIconListener(){
        HRA_IMG_home_mapIcon.setOnClickListener(v->{
            int mapVisibility = mapFlag?View.VISIBLE:View.GONE;
            int requestsVisibility = !mapFlag?View.VISIBLE:View.GONE;
            HRA_VIEW_home_map_view.setVisibility(mapVisibility);
            HRA_LAYOUT_home_requestsLayout.setVisibility(requestsVisibility);
            mapFlag = !mapFlag;
        });
    }

    void findViews(View view){
        this.HRA_LAYOUT_home_categoryButtonContainer = view.findViewById(R.id.HRA_LAYOUT_home_categoryButtonContainer);
        this.HRA_VIEW_home_recyclerView = view.findViewById(R.id.HRA_VIEW_home_recyclerView);
        this.HRA_IMG_home_mapIcon = view.findViewById(R.id.HRA_IMG_home_mapIcon);
        this.HRA_VIEW_home_map_view = view.findViewById(R.id.HRA_VIEW_home_map_view);
        this.HRA_LAYOUT_home_requestsLayout = view.findViewById(R.id.HRA_LAYOUT_home_requestsLayout);

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
