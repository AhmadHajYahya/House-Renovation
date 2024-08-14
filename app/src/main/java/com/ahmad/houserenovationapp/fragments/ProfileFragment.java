package com.ahmad.houserenovationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.LoginActivity;
import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.logic.DataBaseManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment  extends Fragment {
    private RelativeLayout HRA_LAYOUT_profile_logoutButtonContainer;
    private RelativeLayout HRA_LAYOUT_profile_category;
    private ImageButton editPersonalNameButton;
    private ImageButton editPasswordButton;
    private ImageButton editAddressButton;
    private ImageButton editCategoryButton;
    private ImageButton editPhoneNumberButton;

    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        logoutListener();

//        Bundle bundle = getArguments();
//        String userType = bundle.getString("USER_TYPE");
        //userId = bundle.getString("USER_ID");
        String userType = UserType.WORKER.name();
        if(userType.equals(UserType.CUSTOMER.name())){
            HRA_LAYOUT_profile_category.setVisibility(View.GONE);
        }

        listeners();
        // Inflate the layout for this fragment
        return view;
    }

    void logoutListener(){
        HRA_LAYOUT_profile_logoutButtonContainer.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }

    void viewUserData(){

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
    }
}
