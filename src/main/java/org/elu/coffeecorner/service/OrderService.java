package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;

import java.util.List;

public interface OrderService {
    void addProduct(Customer customer, Product product);
    List<Product> getOrder(Customer customer);
}
