package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.repository.OrderRepository;
import org.elu.coffeecorner.utils.AssertUtils;

import java.util.List;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addProduct(final Customer customer, final Product product) {
        addProduct(customer, product, product.extras());
    }

    @Override
    public void addProduct(final Customer customer, final Product product, Set<Extra> withExtras) {
        AssertUtils.assertNotNull("product", product);
        AssertUtils.assertNotNull("extra", withExtras);
        final var productToAdd = new Product(product.name(), product.productType(), product.price(), withExtras);
        orderRepository.addProduct(customer, productToAdd);
    }

    @Override
    public List<Product> getOrder(Customer customer) {
        return orderRepository.getProducts(customer);
    }
}
