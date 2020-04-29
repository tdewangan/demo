package com.test.demo.service;

import com.test.demo.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @MockBean
    ProductService productService;

    @Test
    public void when_get_products_should_return_products() {
        Map<Integer, Product> products = new HashMap<>();
        products.put(1, new Product(1, "Toothpaste"));
        products.put(2, new Product(2, "Rice"));
        when(productService.getProducts()).thenReturn(products);
        Map<Integer, Product> map = productService.getProducts();
        assertThat(map.get(1).getName()).isSameAs("Toothpaste");
    }
}
