package com.ahmad.houserenovationapp.model;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;

import java.util.List;

/** @noinspection ALL*/
public class User {
    private String id;
    private String username;
    private String personalName;
    private String password;
    private String address;
    private String phoneNumber;
    private Category workCategory;
    private double rating;
    private boolean isWorking;
    private List<User> favoriteWorkers;
    private UserType userType;


    public User() {

    }

    // Private constructor to enforce object creation through the builder
    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.personalName = builder.personalName;
        this.password = builder.password;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.workCategory = builder.workCategory;
        this.rating = builder.rating;
        this.isWorking = builder.isWorking;
        this.favoriteWorkers = builder.favoriteWorkers;
        this.userType = builder.userType;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonalName() {
        return personalName;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Category getWorkCategory() {
        return workCategory;
    }

    public double getRating() {
        return rating;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public List<User> getFavoriteWorkers() {
        return favoriteWorkers;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", personalName='" + personalName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workCategory=" + workCategory +
                ", rating=" + rating +
                ", isWorking=" + isWorking +
                ", favoriteWorkers=" + favoriteWorkers +
                ", userType=" + userType +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String id;
        private String username;
        private String personalName;
        private String password;
        private String address;
        private String phoneNumber;
        private Category workCategory;
        private double rating;
        private boolean isWorking;
        private List<User> favoriteWorkers;
        private UserType userType;

        // Setter methods for the builder, returning Builder for method chaining
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPersonalName(String personalName) {
            this.personalName = personalName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setWorkCategory(Category workCategory) {
            this.workCategory = workCategory;
            return this;
        }

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setIsWorking(boolean isWorking) {
            this.isWorking = isWorking;
            return this;
        }

        public Builder setFavoriteWorkers(List<User> favoriteWorkers) {
            this.favoriteWorkers = favoriteWorkers;
            return this;
        }

        public Builder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        // Build method to create an instance of User
        public User build() {
            return new User(this);
        }
    }
}
