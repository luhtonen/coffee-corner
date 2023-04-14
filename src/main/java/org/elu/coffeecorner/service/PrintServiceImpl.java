package org.elu.coffeecorner.service;

import org.elu.coffeecorner.model.Extra;
import org.elu.coffeecorner.model.ExtraDiscount;
import org.elu.coffeecorner.model.Product;
import org.elu.coffeecorner.model.ProductType;
import org.elu.coffeecorner.utils.AssertUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PrintServiceImpl implements PrintService {
    private static final String LINE_END = "\n";
    private static final String LINE = "----------------------------------------------------";
    private static final String TITLE = "      \uD83C\uDD72\uD83C\uDD77\uD83C\uDD70\uD83C\uDD81\uD83C\uDD7B\uD83C\uDD74\uD83C\uDD7D\uD83C\uDD74❜\uD83C\uDD82 \uD83C\uDD72\uD83C\uDD7E\uD83C\uDD75\uD83C\uDD75\uD83C\uDD74\uD83C\uDD74 \uD83C\uDD72\uD83C\uDD7E\uD83C\uDD81\uD83C\uDD7D\uD83C\uDD74\uD83C\uDD81";
    private static final String HEADERS = " Product                      Price    Disc   Total";
    private static final String PRODUCT = " %-26s %7.2f %7s %7.2f";
    private static final String EXTRA = "  + %-23s %7.2f %7s %7.2f";
    private static final String TOTAL = " Total:%44.2f";
    private static final String DATE = " %1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS";

    public PrintServiceImpl() {
        Locale.setDefault(new Locale("de", "CH"));
    }

    @Override
    public String printReceipt(final List<Product> order) {
        AssertUtils.assertNotEmpty("order", order);
        final var now = LocalDateTime.now();
        final var comboDiscounts = calculateComboDiscount(order);
        final BigDecimal total = calculateTotal(order, comboDiscounts);

        return String.join(LINE_END, List.of(
            TITLE,
            LINE,
            HEADERS,
            LINE_END,
            buildProducts(order, comboDiscounts),
            LINE_END,
            TOTAL.formatted(total),
            LINE,
            DATE.formatted(now)
        ));
    }

    String buildProducts(final List<Product> order, final List<ExtraDiscount> comboDiscounts) {
        return order.stream()
                    .map(p -> buildProductLines(p, comboDiscounts))
                    .collect(Collectors.joining(LINE_END));
    }

    String buildProductLines(final Product product, final List<ExtraDiscount> comboDiscounts) {
        final var builder = new StringBuilder();
        final var pos = product.name().length() > 26 ? product.name().lastIndexOf(" ", 26) : -1;
        final var nameEnd = pos > 0 ? product.name().substring(pos + 1) : product.name();
        if (product.name().length() > 26) {
            builder.append(" " + product.name(), 0, pos + 1);
            builder.append(LINE_END);
        }
        final var total = product.discounted() ? BigDecimal.ZERO : product.price();
        final var discountFormatted = product.discounted() ?
            product.price().setScale(2, RoundingMode.HALF_UP).negate() :
            "";
        builder.append(PRODUCT.formatted(nameEnd, product.price(), discountFormatted, total));
        if (!product.extras().isEmpty()) {
            builder.append(LINE_END);
            builder.append(product.extras().stream()
                                  .map(e -> {
                                      final var extraDiscount = comboDiscounts.stream()
                                                                              .filter(c -> !c.isUsed() && c.getPrice().equals(e.price()))
                                                                              .findFirst()
                                                                              .orElse(null);
                                      if (extraDiscount != null) {
                                          extraDiscount.use();
                                      }
                                      final var discountValue = extraDiscount != null ?
                                          extraDiscount.getPrice().setScale(2, RoundingMode.HALF_UP).negate() :
                                          "";
                                      final var extraTotal = extraDiscount != null ?
                                          e.price().subtract(extraDiscount.getPrice()) :
                                          e.price();
                                      return EXTRA.formatted(e.name(), e.price(), discountValue, extraTotal);
                                  })
                                  .collect(Collectors.joining(LINE_END)));
        }
        return builder.toString();
    }

    BigDecimal calculateTotal(final List<Product> order, final List<ExtraDiscount> comboDiscounts) {
        final var comboDiscount = comboDiscounts.stream()
                                                .map(ExtraDiscount::getPrice)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final var priceTotal = order.stream()
                                    .filter(p -> !p.discounted())
                                    .map(Product::price)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        final var extraTotal = order.stream()
                                    .flatMap(p -> p.extras().stream())
                                    .map(Extra::price)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        return priceTotal.add(extraTotal).subtract(comboDiscount);
    }

    List<ExtraDiscount> calculateComboDiscount(final List<Product> products) {
        final var snacks = products.stream().filter(p -> p.productType() == ProductType.Snack).count();
        final var comboCount = Math.min(products.size() - snacks, snacks);
        return comboCount > 0 ?
            products.stream()
                    .flatMap(p -> p.extras().stream())
                    .sorted(Comparator.comparing(Extra::price))
                    .limit(comboCount)
                    .map(e -> new ExtraDiscount(e.price()))
                    .toList() :
            List.of();
    }
}
