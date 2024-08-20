package com.ahmad.houserenovationapp.model;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.UserType;

import java.util.ArrayList;
import java.util.List;
/** @noinspection ALL*/
public class User {
    private String id;
    private String email;
    private String personalName;
    private String password;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private Category workCategory;
    private double rating;
    private int numberOfRatings;
    private boolean working;
    private UserType userType;
    private List<String> favoriteWorkers; // List of favorite worker IDs

    public User() {
        this.favoriteWorkers = new ArrayList<>();
    }

    // Private constructor to enforce object creation through the builder
    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.personalName = builder.personalName;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.workCategory = builder.workCategory;
        this.rating = builder.rating;
        this.numberOfRatings = builder.numberOfRatings;
        this.working = builder.working;
        this.userType = builder.userType;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.favoriteWorkers = builder.favoriteWorkers != null ? builder.favoriteWorkers : new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalName() {
        return personalName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Category getWorkCategory() {
        return workCategory;
    }

    public double getRating() {
        return rating;
    }public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public boolean getWorking() {
        return working;
    }

    public UserType getUserType() {
        return userType;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getFavoriteWorkers() {
        return favoriteWorkers;
    }

    // Method to add a worker to the favorite list
    public void addFavoriteWorker(String workerId) {
        if (!favoriteWorkers.contains(workerId)) {
            favoriteWorkers.add(workerId);
        }
    }

    // Method to remove a worker from the favorite list
    public void removeFavoriteWorker(String workerId) {
        favoriteWorkers.remove(workerId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", personalName='" + personalName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workCategory=" + workCategory +
                ", rating=" + rating +
                ", isWorking=" + working +
                ", userType=" + userType +
                ", favoriteWorkers=" + favoriteWorkers +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String id;
        private String email;
        private String personalName;
        private String password;
        private double latitude;
        private double longitude;
        private String phoneNumber;
        private Category workCategory;
        private double rating;
        private int numberOfRatings;
        private boolean working;
        private UserType userType;
        private List<String> favoriteWorkers;

        // Setter methods for the builder, returning Builder for method chaining
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
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
        }public Builder setNumberOfRatings(int numberOfRating) {
            this.numberOfRatings = numberOfRating;
            return this;
        }

        public Builder setWorking(boolean isWorking) {
            this.working = isWorking;
            return this;
        }

        public Builder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder setLocation(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        public Builder setFavoriteWorkers(List<String> favoriteWorkers) {
            this.favoriteWorkers = favoriteWorkers;
            return this;
        }

        // Build method to create an instance of User
        public User build() {
            return new User(this);
        }
    }
}
