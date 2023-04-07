package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Offer;
import org.elu.coffeecorner.model.OfferType;

import java.math.BigDecimal;

public final class AppFixture {
    private AppFixture() {}

    public static Customer mockCustomer() {
        return mockCustomer(1L);
    }

    public static Customer mockCustomer(final long id) {
        return new Customer(id, "John Doe " + id);
    }

    public static Offer mockProduct() {
        return new Offer("Coffee (small)", OfferType.Beverage, new BigDecimal("2.50"));
    }
}
