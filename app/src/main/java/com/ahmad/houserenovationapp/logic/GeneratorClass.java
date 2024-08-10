package com.ahmad.houserenovationapp.logic;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.model.Customer;
import com.ahmad.houserenovationapp.model.Service;
import com.ahmad.houserenovationapp.model.Worker;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class GeneratorClass {


    public static Customer createCustomer(Map<String,String> data){
        String id = UUID.randomUUID().toString();
        return new Customer(id,data.get("username"),data.get("personalName"),data.get("password"),data.get("address"),data.get("phoneNumber"), new ArrayList<>());
    }
    public static Worker createWorker(Map<String,String> data){
        String id = UUID.randomUUID().toString();
        Category category = Category.valueOf(data.get("category"));
        return new Worker(id,data.get("username"),data.get("personalName"),data.get("password"),data.get("address"),data.get("phoneNumber"), category,0.0,false);
    }

    public static Service createService(Map<String,Object> data){
        String id = UUID.randomUUID().toString();
        return new Service(id, (String) data.get("title"), (String) data.get("description"), (Customer) data.get("customer"), (Worker) data.get("worker"));
    }

}