package com.ahmad.houserenovationapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.utils.HelperFunctions;

/** @noinspection ALL*/
public class HomeFragment extends Fragment {
    private LinearLayout HRA_LAYOUT_home_categoryButtonContainer;
    private Button selectedButton = null; // Variable to store the selected button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(view);
        createHorizontalScrollViewButtons();
        return view;
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


    void findViews(View view){
        this.HRA_LAYOUT_home_categoryButtonContainer = view.findViewById(R.id.HRA_LAYOUT_home_categoryButtonContainer);
    }
}
