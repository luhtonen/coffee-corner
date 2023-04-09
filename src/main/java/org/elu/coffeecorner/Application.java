package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.service.OrderService;
import org.elu.coffeecorner.service.PrintService;

import java.util.List;

public class Application {
    private final OrderService orderService;
    private final PrintService printService;
    private long customerId = 0L;

    public Application(OrderService orderService, PrintService printService) {
        this.orderService = orderService;
        this.printService = printService;
    }

    public Customer placeOrder(final String customerName, final List<Product> products) {
        final var customer = new Customer(++customerId, customerName);
        products.forEach(p -> orderService.addProduct(customer, p));
        return customer;
    }

    public String pay(final Customer customer) {
        return printService.printReceipt(orderService.getOrder(customer));
    }
}
