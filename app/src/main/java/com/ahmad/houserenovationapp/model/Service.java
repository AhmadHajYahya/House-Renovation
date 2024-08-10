package com.ahmad.houserenovationapp.model;

import androidx.annotation.NonNull;

public class Service {

    private String id;
    private String title;
    private String description;
    private Customer customer;
    private Worker worker;

    public Service(String id, String title, String description, Customer customer, Worker worker) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.customer = customer;
        this.worker = worker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @NonNull
    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", customer=" + customer +
                ", worker=" + worker +
                '}';
    }
}
