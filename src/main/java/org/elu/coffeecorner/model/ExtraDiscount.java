package org.elu.coffeecorner.model;

import java.math.BigDecimal;

public class ExtraDiscount {
    private final BigDecimal price;
    private boolean used;

    public ExtraDiscount(BigDecimal price) {
        this.price = price;
        used = false;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isUsed() {
        return used;
    }

    public void use() {
        used = false;
    }
}
