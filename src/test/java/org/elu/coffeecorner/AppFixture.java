package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.model.ProductType;

import java.math.BigDecimal;
import java.util.Set;

public final class AppFixture {
    private AppFixture() {}

    public static Customer mockCustomer() {
        return mockCustomer(1L);
    }

    public static Customer mockCustomer(final long id) {
        return new Customer(id, "John " + id);
    }

    public static Product mockProduct() {
        return mockProduct(Set.of());
    }

    public static Product mockProduct(final Set<Extra> extras) {
        return new Product("Coffee (small)", ProductType.Coffee, BigDecimal.valueOf(2.50), extras);
    }

    public static Product mockSnack() {
        return new Product("Bacon Roll", ProductType.Snack, BigDecimal.valueOf(4.50));
    }

    public static Extra mockExtra(final String name) {
        return mockExtra(name, 0.30);
    }

    public static Extra mockExtra(final String name, final double price) {
        return new Extra(name, ProductType.Coffee, BigDecimal.valueOf(price));
    }
}
