package com.ahmad.houserenovationapp.logic;

import com.ahmad.houserenovationapp.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseManager {

    private static FirebaseDatabase database;
    private static DatabaseReference usersRef;

    static {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
    }


    public static void saveUser(User user){
        usersRef.child(user.getId()).setValue(user);
    }

    public static void updateUserData(String userId, String key, String value){
        usersRef.child(userId).child(key).setValue(value);
    }
}
