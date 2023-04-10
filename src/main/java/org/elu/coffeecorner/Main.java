package org.elu.coffeecorner;

import org.elu.coffeecorner.repository.OrderRepositoryImpl;
import org.elu.coffeecorner.service.OrderServiceImpl;
import org.elu.coffeecorner.service.PrintServiceImpl;
import org.elu.coffeecorner.service.ProductServiceImpl;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        final var app = new Application(new OrderServiceImpl(new OrderRepositoryImpl()),
                                        new PrintServiceImpl(),
                                        new ProductServiceImpl());

        final var extraMilk = app.getAvailableExtras().stream()
                                 .filter(e -> e.name().equals("Extra milk"))
                                 .findFirst()
                                 .orElseThrow();
        final var smallCoffeeWithExtra = app.prepareProduct("Coffee (small)", Set.of(extraMilk), false);
        final var smallCoffee = app.prepareProduct("Coffee (small)", Set.of(), true);
        final var snack = app.prepareProduct("Bacon Roll", Set.of(), false);
        final var juice = app.prepareProduct("Freshly squeezed orange juice", Set.of(), false);

        final var customer = app.startOrder("John");
        app.placeOrder(customer, List.of(smallCoffeeWithExtra, smallCoffee, snack, juice));
        final var receipt = app.pay(customer);
        System.out.println(receipt);
    }
}
