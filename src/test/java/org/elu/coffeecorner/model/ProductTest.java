package org.elu.coffeecorner.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    public static final String PRODUCT_NAME = "Coffee (small)";

    @Nested
    @DisplayName("Product constructor")
    class TestCreateProduct {
        @Test
        @DisplayName("should throw an exception when name is null")
        void testCreateNameNull() {
            assertThatThrownBy(() -> new Product(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'name' must not be null");
        }

        @Test
        @DisplayName("should throw an exception when product type is null")
        void testCreateTypeNull() {
            assertThatThrownBy(() -> new Product(PRODUCT_NAME, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'productType' must not be null");
        }

        @Test
        @DisplayName("should throw an exception when price is null")
        void testCreatePriceNull() {
            assertThatThrownBy(() -> new Product(PRODUCT_NAME, ProductType.Coffee, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'price' must not be null");
        }

        @Test
        @DisplayName("should throw an exception when extras have mixed types")
        void testCreateMixedExtras() {
            final var extras = Set.of(
                new Extra("Extra milk", ProductType.Coffee, new BigDecimal("0.30")),
                new Extra("Extra juice", ProductType.Beverage, new BigDecimal("0.30"))
            );

            assertThatThrownBy(() -> new Product(PRODUCT_NAME, ProductType.Coffee, new BigDecimal("2.50"), extras))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot mix extras for different type of products");
        }

        @Test
        @DisplayName("should throw an exception when extras are for different product type")
        void testCreateExtrasForIncorrectProductType() {
            final var extras = Set.of(
                new Extra("Extra milk", ProductType.Beverage, new BigDecimal("0.30")),
                new Extra("Extra juice", ProductType.Beverage, new BigDecimal("0.30"))
            );

            assertThatThrownBy(() -> new Product(PRODUCT_NAME, ProductType.Coffee, new BigDecimal("2.50"), extras))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Extras cannot be added for this product type");
        }

        @Test
        @DisplayName("should successfully create a product without extras")
        void testCreateWithoutExtras() {
            final var price = new BigDecimal("2.50");

            final var product = new Product(PRODUCT_NAME, ProductType.Coffee, price);

            assertThat(product).isNotNull();
            assertThat(product.name()).isEqualTo(PRODUCT_NAME);
            assertThat(product.productType()).isEqualTo(ProductType.Coffee);
            assertThat(product.price()).isEqualTo(price);
            assertThat(product.extras()).isEmpty();
        }

        @Test
        @DisplayName("should successfully create a product without extras")
        void testCreateWithExtras() {
            final var price = new BigDecimal("2.50");
            final var extras = Set.of(
                new Extra("Extra milk", ProductType.Coffee, new BigDecimal("0.30")),
                new Extra("Foamed milk", ProductType.Coffee, new BigDecimal("0.50"))
            );

            final var product = new Product(PRODUCT_NAME, ProductType.Coffee, price, extras);

            assertThat(product).isNotNull();
            assertThat(product.name()).isEqualTo(PRODUCT_NAME);
            assertThat(product.productType()).isEqualTo(ProductType.Coffee);
            assertThat(product.price()).isEqualTo(price);
            assertThat(product.extras()).hasSameSizeAs(extras);
        }
    }
}
