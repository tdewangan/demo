package com.test.demo.model;

import java.util.ArrayList;

public class ShoppingCart {
    User user;
    ArrayList<Product> products;

    public ShoppingCart() {
    }

    public ShoppingCart(User user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
