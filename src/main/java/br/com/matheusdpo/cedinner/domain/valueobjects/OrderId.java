package br.com.matheusdpo.cedinner.domain.valueobjects;

import java.util.Objects;

public class OrderId {
    private final Long value;

    public OrderId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Order ID must be a positive number");
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
        OrderId orderId = (OrderId) obj;
        return Objects.equals(value, orderId.value);
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
