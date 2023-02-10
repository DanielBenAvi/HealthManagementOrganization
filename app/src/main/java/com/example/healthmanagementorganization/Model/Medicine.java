package com.example.healthmanagementorganization.Model;

public class Medicine {
    String name;
    String price;

    public Medicine() {

    }

    public String getName() {
        return name;
    }

    public Medicine setName(String name) {
        this.name = name;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Medicine setPrice(String price) {
        this.price = price;
        return this;
    }
}
