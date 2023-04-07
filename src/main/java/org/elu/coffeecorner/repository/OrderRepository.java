package org.elu.coffeecorner.repository;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Product;

import java.util.List;

public interface OrderRepository {
    void addProduct(Customer customer, Product product);
    List<Product> getProducts(Customer customer);
}
