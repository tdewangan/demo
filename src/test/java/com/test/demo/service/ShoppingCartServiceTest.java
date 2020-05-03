package com.test.demo.service;

import com.test.demo.model.Product;
import com.test.demo.model.ShoppingCart;
import com.test.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownServiceException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    ShoppingCartService shoppingCartService;

    @BeforeEach
    void init() {
        Map<Integer, ShoppingCart> shoppingCart = shoppingCartService.getShoppingCart();
        shoppingCart.clear();
    }

    @Test
    void addItem() {
        User user = new User();
        user.setId(7);
        Product product = new Product();
        product.setName("Toothpaste");
        product.setId(1);
        assertEquals(0, shoppingCartService.getShoppingCart(user.getId()).getProducts().size(), "Shopping cart should be Empty");
        assertTrue(shoppingCartService.addItem(user, product));
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user.getId());
        assertEquals("Toothpaste", shoppingCart.getProducts().get(0).getName());
        assertEquals(1, shoppingCart.getProducts().size(), "Shopping cart should have 1 product for the given user");
    }

    @Test
    void getShoppingCartForUser() {
        User user = new User();
        user.setId(7);
        Product product = new Product();
        product.setName("Toothpaste");
        product.setId(1);
        assertEquals(0, shoppingCartService.getShoppingCart(user.getId()).getProducts().size(), "Shopping cart should be Empty");
        assertTrue(shoppingCartService.addItem(user, product));
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user.getId());
        assertEquals("Toothpaste", shoppingCart.getProducts().get(0).getName());
        assertEquals(1, shoppingCart.getProducts().size(), "Shopping cart should have 1 product for the given user");
    }

    @Test
    void getShoppingCart() {
        User user1 = new User();
        user1.setId(7);
        Product product = new Product();
        product.setName("Toothpaste");
        product.setId(1);
        User user2 = new User();
        user2.setId(8);
        Product product2 = new Product();
        product2.setName("Soap");
        shoppingCartService.addItem(user1, product);
        shoppingCartService.addItem(user2, product2);
        shoppingCartService.addItem(user2, product2);
        Map<Integer, ShoppingCart> shoppingCart = shoppingCartService.getShoppingCart();
        assertEquals(2, shoppingCart.size());
        assertEquals(2, shoppingCart.get(user2.getId()).getProducts().size(), "Cart item for user2 should be 2");
        assertEquals(1, shoppingCart.get(user1.getId()).getProducts().size(), "Cart item for user1 should be 1");
    }
}