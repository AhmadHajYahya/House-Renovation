package com.ahmad.houserenovationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.LoginActivity;
import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.ahmad.houserenovationapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment  extends Fragment {
    private RelativeLayout HRA_LAYOUT_profile_logoutButtonContainer;
    private RelativeLayout HRA_LAYOUT_profile_category;
    private ImageButton editPersonalNameButton;
    private ImageButton editPasswordButton;
    private ImageButton editAddressButton;
    private ImageButton editCategoryButton;
    private ImageButton editPhoneNumberButton;
    private TextView HRA_TXT_profile_personalNmeTextView;
    private TextView HRA_TXT_personalNameTextView;
    private TextView HRA_TXT_addressTextView;
    private TextView HRA_TXT_phoneNumberTextView;
    private TextView HRA_TXT_categoryTextView;

    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        logoutListener();

        HRA_LAYOUT_profile_category.setVisibility(View.GONE);
        userId = DataBaseManager.getCurrentUser().getId();
        viewUserData(DataBaseManager.getCurrentUser());
        listeners();

        // Inflate the layout for this fragment
        return view;
    }

    void logoutListener(){
        HRA_LAYOUT_profile_logoutButtonContainer.setOnClickListener(v -> {
            DataBaseManager.logoutUser(); // Perform logout using FirebaseAuth
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    void viewUserData(User user){

        HRA_TXT_profile_personalNmeTextView.setText(user.getPersonalName());
        HRA_TXT_personalNameTextView.setText(user.getPersonalName());
        HRA_TXT_addressTextView.setText(user.getAddress());
        HRA_TXT_phoneNumberTextView.setText(user.getPhoneNumber());

        if(user.getUserType().equals(UserType.WORKER)){
            HRA_LAYOUT_profile_category.setVisibility(View.VISIBLE);
            HRA_TXT_categoryTextView.setText(user.getPersonalName());
        }
    }

    void listeners(){
        editPersonalNameButton.setOnClickListener(v->{
            // TODO
            //DataBaseManager.updateUserData(userId,"personalName", value);
        });
        editPasswordButton.setOnClickListener(v->{
            // TODO
            //DataBaseManager.updateUserData(userId,"password", value);
        });
        editAddressButton.setOnClickListener(v->{
            // TODO
            //DataBaseManager.updateUserData(userId,"address", value);
        });
        editCategoryButton.setOnClickListener(v->{
            // TODO
            //DataBaseManager.updateUserData(userId,"category", value);
        });
        editPhoneNumberButton.setOnClickListener(v->{
            // TODO
            //DataBaseManager.updateUserData(userId,"phoneNumber", value);
        });
    }

    void findViews(View view){
        HRA_LAYOUT_profile_logoutButtonContainer = view.findViewById(R.id.HRA_LAYOUT_profile_logoutButtonContainer);
        HRA_LAYOUT_profile_category = view.findViewById(R.id.HRA_LAYOUT_profile_category);
        editPersonalNameButton = view.findViewById(R.id.editPersonalNameButton);
        editPasswordButton = view.findViewById(R.id.editPasswordButton);
        editAddressButton = view.findViewById(R.id.editAddressButton);
        editCategoryButton = view.findViewById(R.id.editCategoryButton);
        editPhoneNumberButton = view.findViewById(R.id.editPhoneNumberButton);
        HRA_TXT_profile_personalNmeTextView = view.findViewById(R.id.HRA_TXT_profile_personalNmeTextView);
        HRA_TXT_personalNameTextView = view.findViewById(R.id.HRA_TXT_personalNameTextView);
        HRA_TXT_addressTextView = view.findViewById(R.id.HRA_TXT_addressTextView);
        HRA_TXT_phoneNumberTextView = view.findViewById(R.id.HRA_TXT_phoneNumberTextView);
        HRA_TXT_categoryTextView = view.findViewById(R.id.HRA_TXT_categoryTextView);
    }
}
