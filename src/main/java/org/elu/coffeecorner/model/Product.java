package org.elu.coffeecorner.model;

import org.elu.coffeecorner.utils.AssertUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public record Product(String name, ProductType productType, BigDecimal price, Set<Extra> extras) {
    public Product {
        AssertUtils.assertNotNull("name", name);
        AssertUtils.assertNotNull("productType", productType);
        AssertUtils.assertNotNull("price", price);
        AssertUtils.assertCondition(extras == null ||
                                        extras.stream().map(Extra::forProduct).distinct().count() <= 1,
                                    "Cannot mix extras for different type of products");
        AssertUtils.assertCondition(extras == null ||
                                        extras.stream().map(Extra::forProduct).allMatch(t -> t == productType),
                                    "Extras cannot be added for this product type");
    }

    public Product(String name, ProductType productType, BigDecimal price) {
        this(name, productType, price, new HashSet<>());
    }
}
