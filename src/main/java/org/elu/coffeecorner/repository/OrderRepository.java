package org.elu.coffeecorner.repository;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Offer;

import java.util.List;

public interface OrderRepository {
    void addProduct(Customer customer, Offer offer);
    List<Offer> getProducts(Customer customer);
}
