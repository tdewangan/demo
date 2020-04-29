package com.test.demo.service;

import com.test.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartService {
    private Map<Integer, ArrayList<Product>> shoppingCart= new HashMap<>();

    public boolean addItem(int userId, Product product) {
        ArrayList cart = shoppingCart.getOrDefault(userId, new ArrayList<Product>());
        cart.add(product);
        shoppingCart.put(userId, cart);
        return true;
    }

    public ArrayList<Product> getShoppingCart(int userId) {
        return shoppingCart.getOrDefault(userId, new ArrayList<>());
    }
}
