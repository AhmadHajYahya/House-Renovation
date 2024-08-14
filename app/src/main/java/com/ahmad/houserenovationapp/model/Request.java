package com.ahmad.houserenovationapp.model;

import com.ahmad.houserenovationapp.enums.Category;

import java.time.LocalDate;

public class Request {

    private String id;
    private String title;
    private String description;
    private Category category;
    private LocalDate date;
    private User customer;
    private User worker;

    // Private constructor to enforce object creation through the builder
    private Request(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.category = builder.category;
        this.date = builder.date;
        this.customer = builder.customer;
        this.worker = builder.worker;
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

    public LocalDate getDate() {
        return date;
    }

    public User getCustomer() {
        return customer;
    }

    public User getWorker() {
        return worker;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", date=" + date +
                ", customer=" + customer +
                ", worker=" + worker +
                '}';
    }

    // Builder Class
    public static class Builder {
        private String id;
        private String title;
        private String description;
        private Category category;
        private LocalDate date;
        private User customer;
        private User worker;

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

        public Builder setDate(LocalDate date) {
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
        }

        // Build method to create an instance of Request
        public Request build() {
            return new Request(this);
        }
    }
}
