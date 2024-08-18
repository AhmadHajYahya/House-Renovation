package com.ahmad.houserenovationapp.logic;

import com.ahmad.houserenovationapp.model.Request;
import com.ahmad.houserenovationapp.model.User;

public class MyDataManager {

    private static User workerToHire;

    private static Request currentJob;

    public static User getWorkerToHire(){
        return workerToHire;
    }

    public static void setWorkerToHire(User u){
        workerToHire = u;
    }

    public static Request getCurrentJob(){
        return currentJob;
    }
    public static void setCurrentJob(Request r){
        currentJob = r;
    }
}
