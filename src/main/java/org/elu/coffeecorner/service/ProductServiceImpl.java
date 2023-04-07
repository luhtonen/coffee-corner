package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.model.ProductType;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private static final List<Product> AVAILABLE_PRODUCTS = List.of(
        new Product("Coffee (small)", ProductType.Coffee, BigDecimal.valueOf(2.50)),
        new Product("Coffee (medium)", ProductType.Coffee, BigDecimal.valueOf(3.00)),
        new Product("Coffee (large)", ProductType.Coffee, BigDecimal.valueOf(3.50)),
        new Product("Freshly squeezed orange juice", ProductType.Beverage, BigDecimal.valueOf(3.95)),
        new Product("Bacon Roll", ProductType.Snack, BigDecimal.valueOf(4.50))
    );
    private static final List<Extra> AVAILABLE_EXTRAS = List.of(
        new Extra("Extra milk", ProductType.Coffee, BigDecimal.valueOf(0.30)),
        new Extra("Foamed milk", ProductType.Coffee, BigDecimal.valueOf(0.50)),
        new Extra("Special roast", ProductType.Coffee, BigDecimal.valueOf(0.90))
    );

    @Override
    public List<Product> getAvailableProducts() {
        return AVAILABLE_PRODUCTS;
    }

    @Override
    public List<Extra> getAvailableExtras() {
        return AVAILABLE_EXTRAS;
    }
}
