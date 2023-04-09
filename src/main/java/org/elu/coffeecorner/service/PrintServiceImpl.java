package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Product;

import java.util.List;

public class PrintServiceImpl implements PrintService {
    private static final String title = "      \uD83C\uDD72\uD83C\uDD77\uD83C\uDD70\uD83C\uDD81\uD83C\uDD7B\uD83C\uDD74\uD83C\uDD7D\uD83C\uDD74❜\uD83C\uDD82 \uD83C\uDD72\uD83C\uDD7E\uD83C\uDD75\uD83C\uDD75\uD83C\uDD74\uD83C\uDD74 \uD83C\uDD72\uD83C\uDD7E\uD83C\uDD81\uD83C\uDD7D\uD83C\uDD74\uD83C\uDD81\n";

    @Override
    public String printReceipt(List<Product> order) {
        return title;
    }
}
