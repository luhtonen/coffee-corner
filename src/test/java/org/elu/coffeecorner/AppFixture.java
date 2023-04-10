package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.model.ProductType;

import java.math.BigDecimal;

public final class AppFixture {
    private AppFixture() {}

    public static Customer mockCustomer() {
        return mockCustomer(1L);
    }

    public static Customer mockCustomer(final long id) {
        return new Customer(id, "John " + id);
    }

    public static Product mockProduct() {
        return new Product("Coffee (small)", ProductType.Coffee, new BigDecimal("2.50"));
    }

    public static Product mockSnack() {
        return new Product("Bacon Roll", ProductType.Snack, new BigDecimal("4.50"));
    }
}
