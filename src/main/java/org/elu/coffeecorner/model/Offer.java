package org.elu.coffeecorner.model;

import org.elu.coffeecorner.utils.AssertUtils;

import java.math.BigDecimal;
import java.util.Set;

public record Offer(String name, OfferType offerType, BigDecimal price, Set<Extra> extras) {
    public Offer {
        AssertUtils.assertNotNull("name", name);
        AssertUtils.assertNotNull("offerType", offerType);
        AssertUtils.assertCondition(extras == null || extras.isEmpty() || offerType != OfferType.Beverage,
                                    "Extras can be added only to beverage");
        AssertUtils.assertNotNull("price", price);
    }

    public Offer(String name, OfferType offerType, BigDecimal price) {
        this(name, offerType, price, Set.of());
    }
}
