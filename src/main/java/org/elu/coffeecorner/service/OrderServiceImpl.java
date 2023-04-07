package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Customer;
import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.repository.OrderRepository;
import org.elu.coffeecorner.utils.AssertUtils;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addProduct(final Customer customer, final Product product) {
        orderRepository.addProduct(customer, product);
    }

    @Override
    public void addExtra(final Product product, final Extra extra) {
        AssertUtils.assertNotNull("product", product);
        AssertUtils.assertNotNull("extra", extra);
        AssertUtils.assertCondition(extra.forProduct() == product.productType(),
                                    "Extra cannot be added to this product type");
        product.extras().add(extra);
    }

    @Override
    public List<Product> getOrder(Customer customer) {
        return orderRepository.getProducts(customer);
    }
}
