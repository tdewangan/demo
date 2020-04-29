package com.test.demo.service;

import com.test.demo.model.Product;
import com.test.demo.model.ShoppingCart;
import com.test.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShoppingCartServiceTest {
    @Autowired
    ShoppingCartService shoppingCartService;

    @Test
    public void addProductForUserAddsProductToShoppingCart() {
        User user = new User();
        user.setId(1);
        Product product = new Product(1, "Toothpaste");
        shoppingCartService.addItem(user, product);
        ShoppingCart shoppingCart =  shoppingCartService.getShoppingCart(user.getId());
        assertThat(shoppingCart.getProducts().get(0).getName()).isSameAs("Toothpaste");
    }

    @Test
    public void whenRequestingShoppingCart_ReturnsUserWithShoppingCartDetails() {
        User user = new User();
        user.setId(1);
        Product product = new Product(1, "Toothpaste");
        shoppingCartService.addItem(user, product);
        Map<Integer, ShoppingCart> shoppingCart =  shoppingCartService.getShoppingCart();
        assertThat(shoppingCart.get(1).getProducts().get(0).getName()).isSameAs("Toothpaste");
    }

    @Test
    public void whenRequestingShoppingCartForUser_ReturnsListOfProduct() {
        User user = new User();
        user.setId(1);
        Product product = new Product(1, "Toothpaste");
        shoppingCartService.addItem(user, product);
        ShoppingCart shoppingCart =  shoppingCartService.getShoppingCart(user.getId());
        assertThat(shoppingCart.getProducts().get(0).getName()).isSameAs("Toothpaste");
    }
}
