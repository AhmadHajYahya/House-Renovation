package com.ahmad.houserenovationapp.model;

import com.ahmad.houserenovationapp.enums.Category;
import com.ahmad.houserenovationapp.enums.Status;
import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;

public class Request {

    private String id;
    private String title;
    private String description;
    private Category category;
    private String date;
    private double latitude;
    private double longitude;
    private User customer;
    private User worker;
    private Status status;
    private boolean workerRated;

    public Request(){

    }
    // Private constructor to enforce object creation through the builder
    private Request(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.category = builder.category;
        this.date = builder.date;
        this.customer = builder.customer;
        this.worker = builder.worker;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.status = builder.status;
        this.workerRated = builder.workerRated;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public User getCustomer() {
        return customer;
    }

    public User getWorker() {
        return worker;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Status getStatus() {
        return status;
    }
    public boolean getWorkerRated() {
        return workerRated;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", date='" + date + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", customer=" + customer +
                ", worker=" + worker +
                ", status=" + status +
                ", workerRated=" + workerRated +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String id;
        private String title;
        private String description;
        private Category category;
        private User customer;
        private User worker;
        private String date;
        private double latitude;
        private double longitude;
        private Status status;
        private boolean  workerRated;

        // Setter methods for the builder, returning Builder for method chaining
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setCustomer(User customer) {
            this.customer = customer;
            return this;
        }

        public Builder setWorker(User worker) {
            this.worker = worker;
            return this;
        }public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }
        public Builder setWorkerRated(boolean value) {
            this.workerRated = value;
            return this;
        }
        public Builder setLocation(double latitude,double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
            return this;
        }

        // Build method to create an instance of Request
        public Request build() {
            return new Request(this);
        }
    }
}
