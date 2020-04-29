package com.test.demo.service;

import com.test.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    public Map<Integer, Product> getProducts() {
        Map<Integer, Product> products = new HashMap<>();
        products.put(1, new Product(1, "Toothpaste"));
        products.put(2, new Product(2, "Rice"));
        return products;
    }
}
