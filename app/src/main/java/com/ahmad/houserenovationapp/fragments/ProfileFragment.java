package com.ahmad.houserenovationapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.ahmad.houserenovationapp.LoginActivity;
import com.ahmad.houserenovationapp.R;
import com.ahmad.houserenovationapp.enums.UserType;

public class ProfileFragment  extends Fragment {
    private RelativeLayout HRA_LAYOUT_profile_logoutButtonContainer;
    private RelativeLayout HRA_LAYOUT_profile_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        logoutListener();

        UserType type = UserType.WORKER; //TODO get user type.

        if(type.equals(UserType.CUSTOMER)){
            HRA_LAYOUT_profile_category.setVisibility(View.GONE);
        }
        // Inflate the layout for this fragment
        return view;
    }

    void logoutListener(){
        HRA_LAYOUT_profile_logoutButtonContainer.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }



    void findViews(View view){
        HRA_LAYOUT_profile_logoutButtonContainer = view.findViewById(R.id.HRA_LAYOUT_profile_logoutButtonContainer);
        HRA_LAYOUT_profile_category = view.findViewById(R.id.HRA_LAYOUT_profile_category);
    }
}
