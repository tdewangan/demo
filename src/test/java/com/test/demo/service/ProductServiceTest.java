package com.test.demo.service;

import com.test.demo.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void getProducts() {
        Map<Integer, Product> expectedProducts = new HashMap<>();
        expectedProducts.put(1, new Product(1, "Toothpaste"));
        expectedProducts.put(2, new Product(2, "Rice"));

        Map<Integer, Product> products = productService.getProducts();
        assertEquals(2, products.size());
//        assertIterableEquals(expectedProducts.values(), products.values());
    }

}