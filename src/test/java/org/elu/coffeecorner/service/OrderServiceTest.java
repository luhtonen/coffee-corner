package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.elu.coffeecorner.AppFixture.mockCustomer;
import static org.elu.coffeecorner.AppFixture.mockExtra;
import static org.elu.coffeecorner.AppFixture.mockExtraFoamedMilk;
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
        final var customer = new Customer(1L, "John Doe");
        final var product = mockProduct();

        service.addProduct(customer, product);

        verify(orderRepository, times(1)).addProduct(any(Customer.class), any(Product.class));
    }

    @Test
    @DisplayName("should throw an exception when product is null")
    void testAddExtraWhenProductNull() {
        assertThatThrownBy(() -> service.addExtra(null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("'product' must not be null");
    }

    @Test
    @DisplayName("should throw an exception when extra is null")
    void testAddExtraWhenExtraNull() {
        assertThatThrownBy(() -> service.addExtra(mockProduct(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("'extra' must not be null");
    }

    @Test
    @DisplayName("should throw an exception when trying to add extra to incorrect product type")
    void testAddExtraToSnack() {
        assertThatThrownBy(() -> service.addExtra(mockSnack(), mockExtra()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Extra cannot be added to this product type");
    }

    @Test
    @DisplayName("should throw an exception when trying to add extra to incorrect product type")
    void testAddExtra() {
        final var product = mockProduct();
        final var extra = mockExtra();
        // not extras
        assertThat(product.extras()).isEmpty();

        // add new extra
        service.addExtra(product, extra);
        assertThat(product.extras()).hasSize(1);

        final var anotherExtra = mockExtraFoamedMilk();
        // add another extra
        service.addExtra(product, anotherExtra);
        assertThat(product.extras()).hasSize(2);

        // add same extra second time
        service.addExtra(product, anotherExtra);
        assertThat(product.extras()).hasSize(2); // no doubles
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
