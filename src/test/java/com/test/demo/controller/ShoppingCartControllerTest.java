package com.test.demo.controller;

import com.test.demo.model.Product;
import com.test.demo.model.ShoppingCart;
import com.test.demo.model.User;
import com.test.demo.model.UserData;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShoppingCartService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RestTemplate restTemplate;

    @MockBean
    private ProductService productService;

    @MockBean
    ShoppingCartService shoppingCartService;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        Map<Integer, Product> products = new HashMap<>();
        products.put(1, new Product(1, "Toothpaste"));
        products.put(2, new Product(2, "Rice"));
        when(productService.getProducts()).thenReturn(products);

        this.mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'name':'Toothpaste'},{'id':2,'name':'Rice'}]"));
    }

    @Nested
    class ShoppingCartTest {
        @Test
        void shouldAddProductInCart() throws Exception {
            User user = new User();
            user.setId(7);
            UserData data = new UserData();
            data.setData(Arrays.asList(user));
            Product product = new Product(1, "Toothpaste");
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            when(restTemplate.getForEntity("https://reqres.in/api/users?page=2", UserData.class))
                    .thenReturn(new ResponseEntity(data, HttpStatus.OK));
            HashMap map = new HashMap();
            map.put(user, Arrays.asList(product));
            when(productService.getProducts()).thenReturn(map);
            when(shoppingCartService.addItem(user, product)).thenReturn(true);


            mockMvc.perform(get("/addItem/7/1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }

        @Test
        void throwUserNotFoundException() throws Exception {
            mockMvc.perform(get("/addItem/1/1"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError())
                    .andExpect(status().reason(containsString("User not found exception")))
                    .andReturn();
        }

        @Test
        public void getShoppingCard() throws Exception{
            User user1 = new User(7);
            User user2 = new User(8);
            Map<Integer, Product> products = new HashMap<>();
            products.put(1, new Product(1, "Toothpaste"));
            products.put(2, new Product(2, "Rice"));
            ShoppingCart shoppingCart = new ShoppingCart(user1, new ArrayList<>());
            shoppingCart.getProducts().add(products.get(0));
            shoppingCart.getProducts().add(products.get(1));

            HashMap<Integer, ShoppingCart> carts = new HashMap<>();
            carts.put(7, shoppingCart);
            carts.put(8, shoppingCart);
            when(shoppingCartService.getShoppingCart()).thenReturn(carts);

            mockMvc.perform(get("/shoppingCart"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("[" +
                            "{'user':{'id':7,'email':null,'first_name':null,'last_name':null,'avatar':null}," +
                            "'products':[null,{'id':1,'name':'Toothpaste'}]}," +
                            "{'user':{'id':7,'email':null,'first_name':null,'last_name':null,'avatar':null}," +
                            "'products':[null,{'id':1,'name':'Toothpaste'}]}]"));
        }
    }


}
