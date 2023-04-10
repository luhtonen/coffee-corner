package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elu.coffeecorner.AppFixture.mockCustomer;
import static org.elu.coffeecorner.AppFixture.mockProduct;
import static org.elu.coffeecorner.AppFixture.mockSnack;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderServiceImpl service;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        service = new OrderServiceImpl(orderRepository);
    }

    @Test
    @DisplayName("should successfully add product to customer order")
    void testAddProduct() {
        final var customer = new Customer(1L, "John");
        final var product = mockProduct();

        service.addProduct(customer, product);

        verify(orderRepository, times(1)).addProduct(any(Customer.class), any(Product.class));
    }

    @Test
    @DisplayName("should retrieve customer order")
    void testGetOrder() {
        final var products = List.of(mockProduct(), mockSnack());
        when(orderRepository.getProducts(any(Customer.class))).thenReturn(products);

        final var result = service.getOrder(mockCustomer());

        assertThat(result).hasSameSizeAs(products);
        verify(orderRepository, times(1)).getProducts(any(Customer.class));
    }
}
