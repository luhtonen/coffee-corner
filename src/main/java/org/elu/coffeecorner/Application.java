package org.elu.coffeecorner;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.service.OrderService;
import org.elu.coffeecorner.service.PrintService;
import org.elu.coffeecorner.service.ProductService;

import java.util.List;
import java.util.Set;

public class Application {
    private final OrderService orderService;
    private final PrintService printService;
    private final ProductService productService;
    private long customerId = 0L;

    public Application(OrderService orderService, PrintService printService, ProductService productService) {
        this.orderService = orderService;
        this.printService = printService;
        this.productService = productService;
    }

    public Customer startOrder(final String customerName) {
        return new Customer(++customerId, customerName);
    }

    public void placeOrder(final Customer customer, final List<Product> products) {
        products.forEach(p -> orderService.addProduct(customer, p));
    }

    public String pay(final Customer customer) {
        return printService.printReceipt(orderService.getOrder(customer));
    }

    public Product prepareProduct(final String productName, Set<Extra> extras, boolean discount) {
        final var offer = getAvailableProducts().stream()
                                                .filter(p -> p.name().equals(productName))
                                                .findFirst()
                                                .orElseThrow();
        return new Product(offer.name(), offer.productType(), offer.price(), extras, discount);
    }

    public List<Product> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    public List<Extra> getAvailableExtras() {
        return productService.getAvailableExtras();
    }
}
