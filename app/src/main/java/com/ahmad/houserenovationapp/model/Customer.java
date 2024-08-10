package com.ahmad.houserenovationapp.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Customer extends User{
    private List<Worker> favoriteWorkers;

    public Customer(String id, String username, String personalName, String password, String address, String phoneNumber, List<Worker> favoriteWorkers) {
        super(id, username, personalName, password, address, phoneNumber);
        this.favoriteWorkers = favoriteWorkers;
    }

    public List<Worker> getFavoriteWorkers() {
        return favoriteWorkers;
    }

    public void setFavoriteWorkers(List<Worker> favoriteWorkers) {
        this.favoriteWorkers = favoriteWorkers;
    }

    @NonNull
    @Override
    public String toString() {
        return "Customer{" +
                "favoriteWorkers=" + favoriteWorkers +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", personalName='" + personalName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
