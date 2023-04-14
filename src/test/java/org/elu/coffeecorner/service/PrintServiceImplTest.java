package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.ExtraDiscount;
import org.elu.coffeecorner.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.elu.coffeecorner.AppFixture.mockExtra;
import static org.elu.coffeecorner.AppFixture.mockProduct;
import static org.elu.coffeecorner.AppFixture.mockSnack;

class PrintServiceImplTest {
    private PrintServiceImpl printService;

    @BeforeEach
    void setup() {
        printService = new PrintServiceImpl();
    }

    @Nested
    @DisplayName("printReceipt()")
    class TestPrintReceipt {
        @Test
        @DisplayName("should throw an exception when order is null")
        void testInputNull() {
            assertThatThrownBy(() -> printService.printReceipt(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'order' must not be null nor empty");
        }

        @Test
        @DisplayName("should throw an exception when order is an empty list")
        void testInputEmpty() {
            assertThatThrownBy(() -> printService.printReceipt(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("'order' must not be null nor empty");
        }
    }

    @Nested
    @DisplayName("calculateTotal()")
    class TestCalculateTotal {
        @Test
        @DisplayName("should calculate total without discount")
        void testWithoutDiscount() {
            final var order = List.of(mockProduct(), mockProduct());

            final var result = printService.calculateTotal(order, List.of());

            assertThat(result).extracting(BigDecimal::doubleValue).isEqualTo(5.0);
        }

        @Test
        @DisplayName("should calculate total without discount")
        void testWithDiscount() {
            final var extra1 = mockExtra("Extra milk", 0.30);
            final var extra2 = mockExtra("Foamed milk", 0.90);
            final var discount = new ExtraDiscount(BigDecimal.valueOf(0.30));
            final var order = List.of(mockProduct(Set.of(extra1, extra2)), mockProduct(Set.of(extra2, extra1)), mockSnack(), mockSnack());
            final var discounts = List.of(discount, discount);

            final var result = printService.calculateTotal(order, discounts);

            assertThat(result).extracting(BigDecimal::doubleValue).isEqualTo(15.8);
        }
    }

    @Nested
    @DisplayName("calculateComboDiscount()")
    class TestCalculateComboDiscount {
        @ParameterizedTest(name = "{1}")
        @DisplayName("should return an empty list when")
        @MethodSource("org.elu.coffeecorner.service.PrintServiceImplTest#extraNoDiscounts")
        void testNoDiscount(final List<Product> input, final String ignore) {
            final var result = printService.calculateComboDiscount(input);

            assertThat(result).isEmpty();
        }

        @ParameterizedTest(name = "{2}")
        @DisplayName("should return")
        @MethodSource("org.elu.coffeecorner.service.PrintServiceImplTest#extraDiscounts")
        void testDiscount(final List<Product> input, final List<ExtraDiscount> expected, final String ignore) {
            final var result = printService.calculateComboDiscount(input);

            assertThat(result).hasSameSizeAs(expected);
            assertThat(result)
                .extracting(ExtraDiscount::getPrice)
                .containsExactlyInAnyOrder(expected.stream().map(ExtraDiscount::getPrice).toArray(BigDecimal[]::new));
        }
    }

    static Stream<Arguments> extraNoDiscounts() {
        return Stream.of(
            Arguments.of(List.of(mockProduct()), "no snacks in order"),
            Arguments.of(List.of(mockSnack()), "no beverage in order"),
            Arguments.of(List.of(mockProduct(), mockSnack()), "when beverage has no extras")
        );
    }

    static Stream<Arguments> extraDiscounts() {
        final var cheapest = 0.30;
        final var extra1 = mockExtra("Extra milk", cheapest);
        final var extra2 = mockExtra("Foamed milk", 0.90);
        final var discount = new ExtraDiscount(BigDecimal.valueOf(cheapest));
        return Stream.of(
            Arguments.of(
                List.of(mockProduct(Set.of(extra1)), mockSnack()), List.of(discount),
                "a list with single discount when beverage has one extra and 1 snack in order"),
            Arguments.of(
                List.of(mockProduct(Set.of(extra1, extra2)), mockSnack()), List.of(discount),
                "a list with single cheapest discount when beverage has multiple extras and 1 snack in order"),
            Arguments.of(
                List.of(mockProduct(Set.of(extra1)), mockProduct(Set.of(extra2)), mockSnack()), List.of(discount),
                "a list with single cheapest discount when 2 beverages with extras and 1 snack in order"),
            Arguments.of(
                List.of(mockProduct(Set.of(extra1, extra2)), mockProduct(Set.of(extra2, extra1)), mockSnack(), mockSnack()), List.of(discount, discount),
                "a list with two cheapest discounts when 2 beverages with extras and 2 snack in order"),
            Arguments.of(
                List.of(mockProduct(Set.of(extra1, extra2)), mockSnack(), mockSnack()), List.of(discount),
                "a list with single cheapest discounts when 1 beverages with extras and 2 snack in order")
        );
    }
}
