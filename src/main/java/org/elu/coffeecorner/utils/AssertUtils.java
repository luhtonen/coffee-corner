package org.elu.coffeecorner.utils;

import java.util.Collection;

public final class AssertUtils {
    private AssertUtils() {}

    public static <T> void assertNotNull(final String field, final T input) {
        if (input == null) {
            throw new IllegalArgumentException("'%s' must not be null".formatted(field));
        }
    }

    public static <T extends Collection<?>> void assertNotEmpty(final String field, final T input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("'%s' must not be null nor empty".formatted(field));
        }
    }

    public static void assertCondition(final boolean condition, final String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
