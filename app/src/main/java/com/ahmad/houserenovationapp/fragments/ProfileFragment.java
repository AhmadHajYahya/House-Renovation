package com.ahmad.houserenovationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.LoginActivity;
import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.model.User;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
/** @noinspection ALL*/
public class ProfileFragment extends Fragment {
    private RelativeLayout HRA_LAYOUT_profile_logoutButtonContainer;
    private RelativeLayout HRA_LAYOUT_profile_category;
    private RelativeLayout HRA_LAYOUT_profile_rating;
    private ImageButton editPersonalNameButton;
    private ImageButton editPasswordButton;
    private ImageButton editCategoryButton;
    private ImageButton editPhoneNumberButton;
    private TextView HRA_TXT_profile_personalNmeTextView;
    private TextView HRA_TXT_personalNameTextView;
    private TextView HRA_TXT_phoneNumberTextView;
    private TextView HRA_TXT_categoryTextView;
    private TextView HRA_TXT_passwordTextView;
    private TextView HRA_TXT_profile_rating;

    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        logoutListener();

        HRA_LAYOUT_profile_category.setVisibility(View.GONE);
        HRA_LAYOUT_profile_rating.setVisibility(View.GONE);
        userId = DataBaseManager.getCurrentUser().getId();
        viewUserData(DataBaseManager.getCurrentUser());
        listeners();

        return view;
    }

    void logoutListener() {
        HRA_LAYOUT_profile_logoutButtonContainer.setOnClickListener(v -> {
            DataBaseManager.logoutUser();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    void viewUserData(User user) {
        HRA_TXT_profile_personalNmeTextView.setText(user.getPersonalName());
        HRA_TXT_personalNameTextView.setText(user.getPersonalName());
        HRA_TXT_phoneNumberTextView.setText(user.getPhoneNumber());
        if (user.getUserType().equals(UserType.WORKER)) {
            HRA_LAYOUT_profile_category.setVisibility(View.VISIBLE);
            HRA_TXT_categoryTextView.setText(user.getWorkCategory().toString());
            HRA_LAYOUT_profile_rating.setVisibility(View.VISIBLE);
            HRA_TXT_profile_rating.setText(getString(R.string.rating1,user.getRating()));

        }
        String stars ="";
        for (int i = 0 ; i < user.getPassword().length();i++){
            stars += "*";
        }
        HRA_TXT_passwordTextView.setText(stars);
    }

    void listeners() {
        editPersonalNameButton.setOnClickListener(v -> showEditDialog("personalName", "Personal Name", HRA_TXT_personalNameTextView.getText().toString()));
        editPasswordButton.setOnClickListener(v -> showEditDialog("password", "Password", ""));
        editCategoryButton.setOnClickListener(v -> showEditDialog("workCategory", "Category", HRA_TXT_categoryTextView.getText().toString()));
        editPhoneNumberButton.setOnClickListener(v -> showEditDialog("phoneNumber", "Phone Number", HRA_TXT_phoneNumberTextView.getText().toString()));
    }

    void showEditDialog(String key, String fieldName, String currentValue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit " + fieldName);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user_info, null);
        builder.setView(dialogView);

        EditText inputField = dialogView.findViewById(R.id.HRA_ETXT_edit_input);
        Spinner categorySpinner = dialogView.findViewById(R.id.HRA_SPINNER_edit_category);
        Button confirmButton = dialogView.findViewById(R.id.HRA_BTN_edit_confirm);

        if (key.equals("workCategory")) {
            inputField.setVisibility(View.GONE);
            categorySpinner.setVisibility(View.VISIBLE);

            // Filter out the ALL value from the Category enum
            Category[] categories = Category.values();
            List<Category> filteredCategories = new ArrayList<>();
            for (Category category : categories) {
                if (!category.equals(Category.ALL)) {
                    filteredCategories.add(category);
                }
            }

            // Set up the spinner with the filtered list
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filteredCategories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            // Set the current category as selected
            Category currentCategory = Category.valueOf(currentValue);
            int spinnerPosition = adapter.getPosition(currentCategory);
            categorySpinner.setSelection(spinnerPosition);
        } else {
            categorySpinner.setVisibility(View.GONE);
            inputField.setVisibility(View.VISIBLE);
            inputField.setText(currentValue);
        }

        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(v -> {
            if (key.equals("workCategory")) {
                Category selectedCategory = (Category) categorySpinner.getSelectedItem();
                String newValue = selectedCategory.name();
                DataBaseManager.updateUserData(userId, key, newValue);
                updateViewAfterEdit(key, newValue);
            } else {
                String newValue = inputField.getText().toString().trim();
                if (!newValue.isEmpty()) {
                    DataBaseManager.updateUserData(userId, key, newValue);
                    updateViewAfterEdit(key, newValue);
                }
            }
            dialog.dismiss();
        });

        dialog.show();
    }



    void updateViewAfterEdit(String key, String newValue) {
        switch (key) {
            case "personalName":
                HRA_TXT_profile_personalNmeTextView.setText(newValue);
                HRA_TXT_personalNameTextView.setText(newValue);
                break;
            case "phoneNumber":
                HRA_TXT_phoneNumberTextView.setText(newValue);
                break;
            case "workCategory":
                HRA_TXT_categoryTextView.setText(newValue);
                break;
            case "password":
                String stars ="";
                for (int i = 0 ; i < newValue.length();i++){
                    stars += "*";
                }
                HRA_TXT_passwordTextView.setText(stars);
                break;
        }
    }

    void findViews(View view) {
        HRA_LAYOUT_profile_logoutButtonContainer = view.findViewById(R.id.HRA_LAYOUT_profile_logoutButtonContainer);
        HRA_LAYOUT_profile_category = view.findViewById(R.id.HRA_LAYOUT_profile_category);
        editPersonalNameButton = view.findViewById(R.id.editPersonalNameButton);
        editPasswordButton = view.findViewById(R.id.editPasswordButton);
        editCategoryButton = view.findViewById(R.id.editCategoryButton);
        editPhoneNumberButton = view.findViewById(R.id.editPhoneNumberButton);
        HRA_TXT_profile_personalNmeTextView = view.findViewById(R.id.HRA_TXT_profile_personalNmeTextView);
        HRA_TXT_personalNameTextView = view.findViewById(R.id.HRA_TXT_personalNameTextView);
        HRA_TXT_phoneNumberTextView = view.findViewById(R.id.HRA_TXT_phoneNumberTextView);
        HRA_TXT_categoryTextView = view.findViewById(R.id.HRA_TXT_categoryTextView);
        HRA_TXT_passwordTextView = view.findViewById(R.id.HRA_TXT_passwordTextView);
        HRA_LAYOUT_profile_rating = view.findViewById(R.id.HRA_LAYOUT_profile_rating);
        HRA_TXT_profile_rating = view.findViewById(R.id.HRA_TXT_profile_rating);
    }
}

