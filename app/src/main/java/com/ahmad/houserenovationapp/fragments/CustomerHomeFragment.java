package com.ahmad.houserenovationapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ahmad.houserenovationapp.LoginActivity;
import com.ahmad.houserenovationapp.MainActivity;
import com.ahmad.houserenovationapp.NewRequestActivity;
import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.adapters.WorkerAdapter;
import com.ahmad.houserenovationapp.callback.WorkerListCallback;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.logic.MyDataManager;
import com.ahmad.houserenovationapp.model.User;
import com.ahmad.houserenovationapp.utils.HelperFunctions;
import com.ahmad.houserenovationapp.utils.MapHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/** @noinspection ALL*/
public class CustomerHomeFragment extends Fragment implements OnMapReadyCallback {
    private LinearLayout HRA_LAYOUT_home_categoryButtonContainer;
    private Button selectedButton = null; // Variable to store the selected button
    private AppCompatImageView HRA_IMG_home_mapIcon;
    private AppCompatImageView HRA_IMG_home_favoriteIcon;
    private RelativeLayout HRA_LAYOUT_customerHome_Workers;
    private MapView HRA_VIEW_home_map_view;
    private boolean mapFlag = false;
    private boolean showFavoritesOnly = false;
    private MapHandler mapHandler;
    private TextInputEditText HRA_ETXT_home_searchEditText;

    private RecyclerView HRA_VIEW_customerHome_recyclerView;
    private WorkerAdapter workerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);

        findViews(view);
        createHorizontalScrollViewButtons();
        mapIconListener();
        favoriteIconListener();
        setupRecyclerView(Category.ALL);

        HRA_VIEW_home_map_view.onCreate(savedInstanceState);
        HRA_VIEW_home_map_view.getMapAsync(this);
        mapHandler = new MapHandler(getActivity(), HRA_VIEW_home_map_view, this);

        setupSearchListener();
        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapHandler.setupMap(googleMap);
        updateMapWithNewData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapHandler.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public void updateMapWithNewData(){
        if(DataBaseManager.getWorkers()!=null){
            mapHandler.getmMap().clear();
            for (User worker : DataBaseManager.getWorkers()){
                LatLng userLatLng = new LatLng(worker.getLatitude(), worker.getLongitude());
                mapHandler.getmMap().addMarker(new MarkerOptions().position(userLatLng).title(worker.getPersonalName()));
            }
        }
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
                button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_dark));
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
                    button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_blue_dark));
                    button.setTextColor(getResources().getColor(android.R.color.white));
                    selectedButton = button; // Store the currently selected button

                    Category selectedCategory = category;
                    updateMapWithNewData();
                    setupRecyclerView( category);
                }

            });
        }
    }

    void mapIconListener(){
        HRA_IMG_home_mapIcon.setOnClickListener(v->{
            int mapVisibility = mapFlag?View.VISIBLE:View.GONE;
            int usersVisibility = !mapFlag?View.VISIBLE:View.GONE;
            HRA_VIEW_home_map_view.setVisibility(mapVisibility);
            HRA_LAYOUT_customerHome_Workers.setVisibility(usersVisibility);
            mapFlag = !mapFlag;
        });
    }
    void favoriteIconListener() {
        HRA_IMG_home_favoriteIcon.setOnClickListener(v -> {
            showFavoritesOnly = !showFavoritesOnly;
            if (showFavoritesOnly) {
                HRA_IMG_home_favoriteIcon.setImageResource(R.drawable.ic_heart_filled); // Set to filled heart
                filterFavorites();
            } else {
                HRA_IMG_home_favoriteIcon.setImageResource(R.drawable.ic_heart); // Set to outline heart
                setupRecyclerView(Category.ALL);
            }
        });
    }
    void setupRecyclerView(Category category) {
        DataBaseManager.getWorkersByCategory(category,new WorkerListCallback() {
            @Override
            public void onWorkerListRetrieved(List<User> workerList) {
                wAdapter( workerList);
            }
        });


    }

    void setupSearchListener() {
        HRA_ETXT_home_searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterWorkersByName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        });
    }

    void filterWorkersByName(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User worker : DataBaseManager.getWorkers()) {
            if (worker.getPersonalName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(worker);
            }
        }

        wAdapter(filteredList);

        // Optionally update the map markers to show only filtered workers
        mapHandler.getmMap().clear();
        for (User worker : filteredList) {
            LatLng userLatLng = new LatLng(worker.getLatitude(), worker.getLongitude());
            mapHandler.getmMap().addMarker(new MarkerOptions().position(userLatLng).title(worker.getPersonalName()));
        }
    }
    void filterFavorites() {
        List<User> favoriteWorkers = new ArrayList<>();
        List<String> favoriteWorkerIds = DataBaseManager.getCurrentUser().getFavoriteWorkers();

        for (User worker : DataBaseManager.getWorkers()) {
            if (favoriteWorkerIds.contains(worker.getId())) {
                favoriteWorkers.add(worker);
            }
        }

        wAdapter(favoriteWorkers);
        updateMapMarkers(favoriteWorkers);
    }
    void updateMapMarkers(List<User> workerList) {
        mapHandler.getmMap().clear();
        for (User worker : workerList) {
            LatLng userLatLng = new LatLng(worker.getLatitude(), worker.getLongitude());
            mapHandler.getmMap().addMarker(new MarkerOptions().position(userLatLng).title(worker.getPersonalName()));
        }
    }
    private void wAdapter(List<User> workerList){
                HRA_VIEW_customerHome_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        workerAdapter = new WorkerAdapter(getActivity(), workerList,new WorkerAdapter.OnHireListener() {

            @Override
            public void onHire(User user, int position) {
                MyDataManager.setWorkerToHire(user);
                Intent intent = new Intent(getActivity(), NewRequestActivity.class);
                startActivity(intent);
//                        workerList.remove(positio;n);
//                        workerAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onFavorite(User user) {
            }
        });
        HRA_VIEW_customerHome_recyclerView.setAdapter(workerAdapter);

    }
    void findViews(View view){
        this.HRA_LAYOUT_home_categoryButtonContainer = view.findViewById(R.id.HRA_LAYOUT_home_categoryButtonContainer);
        this.HRA_IMG_home_mapIcon = view.findViewById(R.id.HRA_IMG_home_mapIcon);
        this.HRA_VIEW_home_map_view = view.findViewById(R.id.HRA_VIEW_home_map_view);
        this.HRA_LAYOUT_customerHome_Workers = view.findViewById(R.id.HRA_LAYOUT_customerHome_Workers);
        this.HRA_VIEW_customerHome_recyclerView = view.findViewById(R.id.HRA_VIEW_customerHome_recyclerView);
        this.HRA_ETXT_home_searchEditText = view.findViewById(R.id.HRA_ETXT_home_searchEditText);
        this.HRA_IMG_home_favoriteIcon = view.findViewById(R.id.HRA_IMG_home_favoriteIcon);
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
