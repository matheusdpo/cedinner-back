package br.com.matheusdpo.cedinner.domain.valueobjects;

import java.util.Objects;

public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductId productId = (ProductId) obj;
        return Objects.equals(value, productId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
