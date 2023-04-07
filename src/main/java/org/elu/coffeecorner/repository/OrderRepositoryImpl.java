package org.elu.coffeecorner.repository;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.utils.AssertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {
    private final Map<Customer, List<Product>> orders;

    public OrderRepositoryImpl() {
        orders = new HashMap<>();
    }

    @Override
    public void addProduct(final Customer customer, final Product product) {
        AssertUtils.assertNotNull("customer", customer);
        AssertUtils.assertNotNull("product", product);
        orders.computeIfAbsent(customer, (ignore) -> new ArrayList<>())
              .add(product);
    }

    @Override
    public List<Product> getProducts(final Customer customer) {
        return customer != null ?
            orders.getOrDefault(customer, new ArrayList<>()) :
            new ArrayList<>();
    }
}
