package org.elu.coffeecorner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {
    private ProductService productService;

    @BeforeEach
    void setup() {
        productService = new ProductServiceImpl();
    }

    @Test
    void testAvailableProducts() {
        final var products = productService.getAvailableProducts();

        assertThat(products).isNotEmpty();
    }

    @Test
    void testAvailableExtras() {
        final var extras = productService.getAvailableExtras();

        assertThat(extras).isNotEmpty();
    }
}
