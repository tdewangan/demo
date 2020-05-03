package com.test.demo.controller;

import com.test.demo.model.Product;
import com.test.demo.model.ShoppingCart;
import com.test.demo.model.User;
import com.test.demo.model.UserData;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class ShoppingCartController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ProductService productService;

    @RequestMapping("/addItem/{userId}/{productId}")
    public ShoppingCart addToShoppingCart(@PathVariable int userId, @PathVariable int productId, HttpServletResponse response) throws Exception {
        UserData userData = restTemplate.getForObject("https://reqres.in/api/users?page=2", UserData.class);
        Optional<User> user = userData.getData().stream().filter((User e) -> e.getId() == userId).findFirst();
        if(user.isPresent()) {
            shoppingCartService.addItem(user.get(), productService.getProducts().get(productId));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found exception");
        }
        return shoppingCartService.getShoppingCart(userId);
    }

    @RequestMapping("/products")
    public List<Product> getProducts() {
        Map<Integer, Product> map = productService.getProducts();
        return map.values().stream().collect(toList());
    }

    @RequestMapping("/shoppingCart")
    public List<ShoppingCart> getShoppingCart() {
        return shoppingCartService.getShoppingCart().values().stream().collect(toList());
    }
}
