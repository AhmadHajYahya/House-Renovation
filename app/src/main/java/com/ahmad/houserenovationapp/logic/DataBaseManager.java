package com.ahmad.houserenovationapp.logic;

import android.view.View;

import androidx.annotation.NonNull;

import com.ahmad.houserenovationapp.callback.UserCallBack;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBaseManager {

    private static FirebaseDatabase database;
    private static DatabaseReference usersRef;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseUser firebaseUser;
    private static User user;


    static {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    public static void saveUser(User user){
        usersRef.child(user.getId()).setValue(user);
    }

    public static void updateUserData(String userId, String key, String value){
        usersRef.child(userId).child(key).setValue(value);
    }

    public static void getUser(UserCallBack callBack){

        usersRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    setCurrentUser(user);
                    callBack.onUserRetrieved(user);
                } else {
                    callBack.onUserRetrieved(null); // Handle case where user is null
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callBack.onUserRetrieved(null); // Handle cancellation or error
            }
        });
    }


    public static User getCurrentUser(){
        return user;
    }

    public static void setCurrentUser(User u){
        user = u;
    }

    public static void logoutUser() {
        firebaseAuth.signOut();  // Sign out the current user
        user = null; // Clear the current user data
    }

}
