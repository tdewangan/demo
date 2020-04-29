package com.test.demo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.model.Product;
import com.test.demo.model.User;
import com.test.demo.model.UserData;
import com.test.demo.service.ProductService;
import com.test.demo.service.ShoppingCartService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest
public class ShoppingCartControllerTest {

    @Autowired
    ShoppingCartController controller;

    @Mock
    private RestTemplate restTemplate;

    @MockBean
    ShoppingCartService shoppingCartService;

    @MockBean
    ProductService productService;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void initialize() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void addProductForUser_returnCartArray() throws Exception {
        mockServer = MockRestServiceServer.createServer(restTemplate);
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
        map.put(7, Arrays.asList(product));
        when(productService.getProducts()).thenReturn(map);
        when(shoppingCartService.addItem(7, product)).thenReturn(true);
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("/addItem/7/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(products))
                );
    }
}
