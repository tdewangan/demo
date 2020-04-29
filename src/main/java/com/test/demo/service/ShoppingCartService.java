package com.test.demo.service;

import com.test.demo.model.Product;
import com.test.demo.model.ShoppingCart;
import com.test.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartService {
    private Map<Integer, ShoppingCart> shoppingCarts = new HashMap<>();

    public boolean addItem(User user, Product product) {
        ShoppingCart cart = shoppingCarts.getOrDefault(user.getId(), new ShoppingCart(user, new ArrayList<>()));
        cart.getProducts().add(product);
        shoppingCarts.put(user.getId(), cart);
        return true;
    }

    public ShoppingCart getShoppingCart(int userId) {
        return shoppingCarts.getOrDefault(userId, new ShoppingCart());
    }

    public Map<Integer, ShoppingCart> getShoppingCart() {
        return shoppingCarts;
    }
}
