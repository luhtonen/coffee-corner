package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.model.ProductType;

import java.math.BigDecimal;

public final class AppFixture {
    private AppFixture() {}

    public static Customer mockCustomer() {
        return mockCustomer(1L);
    }

    public static Customer mockCustomer(final long id) {
        return new Customer(id, "John Doe " + id);
    }

    public static Product mockProduct() {
        return new Product("Coffee (small)", ProductType.Coffee, new BigDecimal("2.50"));
    }

    public static Product mockSnack() {
        return new Product("Bacon Roll", ProductType.Snack, new BigDecimal("4.50"));
    }

    public static Extra mockExtra() {
        return new Extra("Extra milk", ProductType.Coffee, new BigDecimal("0.30"));
    }

    public static Extra mockExtraFoamedMilk() {
        return new Extra("Foamed milk", ProductType.Coffee, new BigDecimal("0.50"));
    }
}
