package com.test.demo.controller;

import com.test.demo.model.Product;
import com.test.demo.model.User;
import com.test.demo.model.UserData;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
public class ShoppingCartController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ProductService productService;

    @RequestMapping("/addItem/{userId}/{productId}")
    public ArrayList<Product> addToShoppingCart(@PathVariable int userId, @PathVariable int productId) {
        UserData userData = restTemplate.getForObject("https://reqres.in/api/users?page=2", UserData.class);
        boolean found = userData.getData().stream().anyMatch((User e) -> e.getId() == userId);
        if(found) {
            shoppingCartService.addItem(userId, productService.getProducts().get(productId));
        }
        return shoppingCartService.getShoppingCart(userId);
    }
}
