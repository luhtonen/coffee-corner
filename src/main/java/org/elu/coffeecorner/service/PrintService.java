package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Product;

import java.util.List;

public interface PrintService {
    String printReceipt(List<Product> order);
}
