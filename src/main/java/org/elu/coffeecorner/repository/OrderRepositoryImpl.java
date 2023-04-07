package org.elu.coffeecorner.repository;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Offer;
import org.elu.coffeecorner.utils.AssertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepository {
    private final Map<Customer, List<Offer>> orders;

    public OrderRepositoryImpl() {
        orders = new HashMap<>();
    }

    @Override
    public void addProduct(final Customer customer, final Offer offer) {
        AssertUtils.assertNotNull("customer", customer);
        AssertUtils.assertNotNull("product", offer);
        orders.computeIfAbsent(customer, (ignore) -> new ArrayList<>())
              .add(offer);
    }

    @Override
    public List<Offer> getProducts(final Customer customer) {
        return customer != null ?
            orders.getOrDefault(customer, new ArrayList<>()) :
            new ArrayList<>();
    }
}
