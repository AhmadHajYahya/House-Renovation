package com.ahmad.houserenovationapp.logic;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;
import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class GeneratorClass {
    public static User createCustomer(Map<String,Object> data){
        //String id = UUID.randomUUID().toString();
        return new User.Builder()
                .setId((String) data.get("userId"))
                .setEmail((String)data.get("email"))
                .setPersonalName((String)data.get("personalName"))
                .setPassword((String)data.get("password"))
                .setPhoneNumber((String)data.get("phoneNumber"))
                .setLocation((double) data.get("latitude"),(double) data.get("longitude"))
                .setUserType(UserType.CUSTOMER)
                .build() ;
    }
    public static User createWorker(Map<String,Object> data){
        //String id = UUID.randomUUID().toString();
        Category category = Category.valueOf((String)data.get("category"));
        return new User.Builder()
                .setId((String)data.get("userId"))
                .setEmail((String)data.get("email"))
                .setPersonalName((String)data.get("personalName"))
                .setPassword((String)data.get("password"))
                .setPhoneNumber((String)data.get("phoneNumber"))
                .setLocation((double) data.get("latitude"),(double) data.get("longitude"))
                .setUserType(UserType.WORKER)
                .setWorkCategory(category)
                .setRating(0.0)
                .setIsWorking(false)
                .build();
    }

    public static Request createService(Map<String,Object> data){
        String id = UUID.randomUUID().toString();
        return new Request.Builder()
                .setId(id)
                .setTitle((String) data.get("title"))
                .setDescription((String) data.get("description"))
                .setCategory((Category) data.get("category"))
                .setLocation((double) data.get("latitude"),(double) data.get("longitude"))
                .setDate(LocalDate.now().toString())
                .setCustomer((User) data.get("customer"))
                .setWorker((User) data.get("worker"))
                .build();
    }
}