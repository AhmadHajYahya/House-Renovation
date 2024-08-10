package com.ahmad.houserenovationapp.model;

import androidx.annotation.NonNull;

import com.ahmad.houserenovationapp.enums.Category;

public class Worker extends User{
    private Category category;
    private double rating;
    private boolean isWorking;


    public Worker(String id, String username, String personalName, String password, String address, String phoneNumber, Category category, double rating, boolean isWorking) {
        super(id, username, personalName, password, address, phoneNumber);
        this.category = category;
        this.rating = rating;
        this.isWorking = isWorking;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @NonNull
    @Override
    public String toString() {
        return "Worker{" +
                "category=" + category +
                ", rating=" + rating +
                ", isWorking=" + isWorking +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", personalName='" + personalName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
