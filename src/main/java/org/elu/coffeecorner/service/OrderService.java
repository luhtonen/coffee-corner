package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;

import java.util.List;
import java.util.Set;

public interface OrderService {
    void addProduct(Customer customer, Product product);
    void addProduct(Customer customer, Product product, Set<Extra> withExtras);
    List<Product> getOrder(Customer customer);
}
