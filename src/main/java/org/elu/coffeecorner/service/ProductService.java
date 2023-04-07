package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAvailableProducts();
    List<Extra> getAvailableExtras();
}
