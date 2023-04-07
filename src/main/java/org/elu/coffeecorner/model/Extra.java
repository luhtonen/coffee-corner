package org.elu.coffeecorner.model;

import java.math.BigDecimal;

public record Extra(String name, ProductType forProduct, BigDecimal price) {
}
