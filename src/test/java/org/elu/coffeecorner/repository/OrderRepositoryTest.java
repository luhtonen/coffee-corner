package org.elu.coffeecorner.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.elu.coffeecorner.AppFixture.mockCustomer;
import static org.elu.coffeecorner.AppFixture.mockProduct;

class OrderRepositoryTest {
    private OrderRepository repository;

    @BeforeEach
    void setup() {
        repository = new OrderRepositoryImpl();
    }

    @Test
    @DisplayName("should throw an exception when customer is null")
    void testAddProductCustomerNull() {
        assertThatThrownBy(() -> repository.addProduct(null, mockProduct()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("'customer' must not be null");
    }

    @Test
    @DisplayName("should throw an exception when product is null")
    void testAddProductWhenProductNull() {
        assertThatThrownBy(() -> repository.addProduct(mockCustomer(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("'product' must not be null");
    }

    @Test
    @DisplayName("should successfully add first product to the new customer")
    void testAddFirstProduct() {
        final var customer = mockCustomer();

        repository.addProduct(mockCustomer(), mockProduct());

        assertThat(repository.getProducts(customer)).hasSize(1);
    }

    @Test
    @DisplayName("should successfully add multiple products to the new customer")
    void testAddMultipleProducts() {
        final var customer = mockCustomer();

        repository.addProduct(customer, mockProduct());
        repository.addProduct(customer, mockProduct());
        repository.addProduct(customer, mockProduct());

        assertThat(repository.getProducts(customer)).hasSize(3);
    }

    @Test
    @DisplayName("should successfully add multiple products to multiple customers")
    void testAddMultipleProductsMultipleCustomer() {
        final var customer1 = mockCustomer(1L);
        final var customer2 = mockCustomer(2L);

        repository.addProduct(customer1, mockProduct());
        repository.addProduct(customer2, mockProduct());
        repository.addProduct(customer2, mockProduct());

        assertThat(repository.getProducts(customer1)).hasSize(1);
        assertThat(repository.getProducts(customer2)).hasSize(2);
    }

    @Test
    @DisplayName("should return an empty list for getProducts when customer is null")
    void testGetProductsWhenCustomerNull() {
        final var result = repository.getProducts(null);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should return an empty list for getProducts when customer does not exist")
    void testNoProducts() {
        final var customer = mockCustomer();

        final var result = repository.getProducts(customer);

        assertThat(result).isEmpty();
    }
}
