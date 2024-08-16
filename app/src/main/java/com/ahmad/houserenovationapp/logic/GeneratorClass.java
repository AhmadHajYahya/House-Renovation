package com.ahmad.houserenovationapp.logic;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;
import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GeneratorClass {
    public static User createCustomer(Map<String,String> data){
        //String id = UUID.randomUUID().toString();
        return new User.Builder()
                .setId(data.get("userId"))
                .setUsername(data.get("username"))
                .setPersonalName(data.get("personalName"))
                .setPassword(data.get("password"))
                .setPhoneNumber(data.get("phoneNumber"))
                .setAddress(data.get("address"))
                .setUserType(UserType.CUSTOMER)
                .build() ;
    }
    public static User createWorker(Map<String,String> data){
        //String id = UUID.randomUUID().toString();
        Category category = Category.valueOf(data.get("category"));
        return new User.Builder()
                .setId(data.get("userId"))
                .setUsername(data.get("username"))
                .setPersonalName(data.get("personalName"))
                .setPassword(data.get("password"))
                .setPhoneNumber(data.get("phoneNumber"))
                .setAddress(data.get("address"))
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
                .setDate(LocalDate.now())
                .setCustomer((User) data.get("customer"))
                .setWorker((User) data.get("worker"))
                .build();
    }
}